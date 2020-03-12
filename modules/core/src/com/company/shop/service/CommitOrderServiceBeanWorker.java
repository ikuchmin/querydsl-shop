package com.company.shop.service;

import com.company.shop.entity.Order;
import com.company.shop.entity.OrderItem;
import com.company.shop.entity.OrderStatus;
import com.company.shop.entity.OrderStorageItem;
import com.company.shop.entity.Product;
import com.company.shop.entity.StorageItem;
import com.company.shop.exception.AppropriateStorageNotFound;
import com.company.shop.service.StorageRepositoryService.StorageForOrder;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TransactionalDataManager;
import com.haulmont.cuba.core.entity.contracts.Id;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CommitOrderServiceBeanWorker {


    private Persistence persistence;
    protected TransactionalDataManager txDm;

    protected Metadata metadata;

    protected OrderRepositoryServiceBeanWorker orderRepository;

    protected StorageRepositoryServiceBeanWorker storageRepository;


    public CommitOrderServiceBeanWorker(Persistence persistence, TransactionalDataManager txDm, Metadata metadata,
                                        OrderRepositoryServiceBeanWorker orderRepository,
                                        StorageRepositoryServiceBeanWorker storageRepository) {
        this.persistence = persistence;
        this.txDm = txDm;
        this.metadata = metadata;
        this.orderRepository = orderRepository;
        this.storageRepository = storageRepository;
    }

    public Order commitOrder(Id<Order, UUID> orderId, String viewName) {

        View storageItemWithProductView = ViewBuilder.of(StorageItem.class)
                .addView(View.LOCAL)
                .add("product", View.MINIMAL)
                .build();

        List<StorageForOrder> appropriateStorages = storageRepository
                 .findStoragesWhichCanProvideOrder(orderId, View.MINIMAL, storageItemWithProductView);

        if (appropriateStorages.isEmpty()) {
            throw new AppropriateStorageNotFound(orderId);
        }

        StorageForOrder selectedStorage = appropriateStorages.get(0);

        // mark order as submitted
        View orderView = ViewBuilder.of(Order.class)
                .addView(viewName)
                .add("orderStatus")
                .add("orderItems", vb -> vb
                        .addView(View.LOCAL)
                        .add("product", View.MINIMAL))
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

            Order savedOrder = txDm.save(order, viewName);

            tx.commit();

            return savedOrder;
        }
    }
}
