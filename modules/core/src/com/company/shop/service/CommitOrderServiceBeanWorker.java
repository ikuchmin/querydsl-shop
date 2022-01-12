package com.company.shop.service;

import com.company.shop.entity.Order;
import com.company.shop.entity.OrderItem;
import com.company.shop.entity.OrderStatus;
import com.company.shop.entity.OrderStorageItem;
import com.company.shop.entity.Product;
import com.company.shop.entity.QOrder;
import com.company.shop.entity.QStorageItem;
import com.company.shop.entity.StorageItem;
import com.company.shop.exception.AppropriateStorageNotFound;
import com.company.shop.service.StorageRepositoryService.StorageForOrder;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TransactionalDataManager;
import com.haulmont.cuba.core.entity.contracts.Id;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewRepository;
import org.springframework.stereotype.Component;
import ru.udya.querydsl.cuba.core.view.TypedViewFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CommitOrderServiceBeanWorker {

    protected Persistence persistence;
    protected TransactionalDataManager txDm;

    protected ViewRepository viewRepository;
    protected Metadata metadata;

    protected TypedViewFactory typedViewFactory;

    protected OrderRepositoryServiceBeanWorker orderRepository;

    protected StorageRepositoryServiceBeanWorker storageRepository;


    public CommitOrderServiceBeanWorker(Persistence persistence, TransactionalDataManager txDm,
                                        ViewRepository viewRepository, Metadata metadata,
                                        TypedViewFactory typedViewFactory,
                                        OrderRepositoryServiceBeanWorker orderRepository,
                                        StorageRepositoryServiceBeanWorker storageRepository) {
        this.persistence = persistence;
        this.txDm = txDm;
        this.viewRepository = viewRepository;
        this.metadata = metadata;
        this.typedViewFactory = typedViewFactory;
        this.orderRepository = orderRepository;
        this.storageRepository = storageRepository;
    }

    public Order commitOrder(Id<Order, UUID> orderId, String view) {
        return commitOrder(orderId, viewRepository.getView(Order.class, view));
    }

    public Order commitOrder(Id<Order, UUID> orderId, View view) {

        var qStorageItem = QStorageItem.storageItem;
        View storageItemWithProductView = typedViewFactory
                .view(qStorageItem).extendByViews(View.LOCAL)
                .property(qStorageItem.product, (qp, qpb) -> qpb.extendByViews(View.MINIMAL))
                .build();

        List<StorageForOrder> appropriateStorages = storageRepository
                 .findStoragesWhichCanProvideOrder(orderId, View.MINIMAL, storageItemWithProductView);

        if (appropriateStorages.isEmpty()) {
            throw new AppropriateStorageNotFound(orderId);
        }

        StorageForOrder selectedStorage = appropriateStorages.get(0);

        // mark order as submitted
        var qOrder = QOrder.order;
        View orderView = typedViewFactory
                .view(qOrder).extendByViews(view)
                .property(qOrder.orderStatus)
                .property(qOrder.orderItems, (oi, oib) -> oib.extendByViews(View.LOCAL)
                        .property(oi.product, (p, pb) -> pb.extendByViews(View.MINIMAL)))
                .build();

        Order order = orderRepository.findOrderByIdNN(orderId, orderView);
        order.setOrderStatus(OrderStatus.RESERVED);

        // map storage items on order items
        Map<Product, List<StorageItem>> storageItemsByProduct =
                selectedStorage.appropriateStorageItems.stream()
                        .collect(Collectors.groupingBy(StorageItem::getProduct));

        Map<OrderItem, StorageItem> mappingOrderItemOnStorageItem =
                new HashMap<>(order.getOrderItems().size());
        for (OrderItem orderItem : order.getOrderItems()) {
            // we have a guarantees that we have at least one storage item for order
            StorageItem storageItemForOrderItem =
                    storageItemsByProduct.get(orderItem.getProduct()).get(0);

            mappingOrderItemOnStorageItem.put(orderItem, storageItemForOrderItem);
        }

        // reduce count into storage by order
        List<StorageItem> reducedStorageItems =
                new ArrayList<>(mappingOrderItemOnStorageItem.size());
        for (Map.Entry<OrderItem, StorageItem> orderItemStorageItemEntry
                : mappingOrderItemOnStorageItem.entrySet()) {

            StorageItem storageItem = orderItemStorageItemEntry.getValue();
            OrderItem orderItem = orderItemStorageItemEntry.getKey();

            storageItem.setCount(storageItem.getCount() - orderItem.getCount());

            reducedStorageItems.add(storageItem);
        }

        OrderStorageItem orderStorageItem = metadata.create(OrderStorageItem.class);
        orderStorageItem.setOrder(order);
        orderStorageItem.setStorage(selectedStorage.getAppropriateStorage());

        try (Transaction tx = persistence.getTransaction()){

            txDm.save(reducedStorageItems.toArray(new StorageItem[0]));
            txDm.save(orderStorageItem);

            Order savedOrder = txDm.save(order, view);

            tx.commit();

            return savedOrder;
        }
    }
}
