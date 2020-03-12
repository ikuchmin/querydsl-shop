package com.company.shop.service;

import com.company.shop.entity.Order;
import com.company.shop.entity.OrderItem;
import com.company.shop.entity.Product;
import com.company.shop.entity.QOrderItem;
import com.company.shop.entity.QStorageItem;
import com.company.shop.entity.Storage;
import com.company.shop.entity.StorageItem;
import com.company.shop.service.StorageRepositoryService.StorageForOrder;
import com.haulmont.cuba.core.TransactionalDataManager;
import com.haulmont.cuba.core.entity.contracts.Id;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import com.haulmont.cuba.core.global.ViewRepository;
import org.springframework.stereotype.Component;
import ru.udya.querydsl.cuba.core.CubaQueryFactory;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

@Component
public class StorageRepositoryServiceBeanWorker {

    protected TransactionalDataManager txDm;

    protected Metadata metadata;
    private ViewRepository viewRepository;
    private OrderItemRepositoryServiceBeanWorker orderItemRepository;

    public StorageRepositoryServiceBeanWorker(TransactionalDataManager txDm,
                                              Metadata metadata,
                                              ViewRepository viewRepository,
                                              OrderItemRepositoryServiceBeanWorker orderItemRepository) {
        this.txDm = txDm;
        this.metadata = metadata;
        this.viewRepository = viewRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public List<StorageForOrder> findStoragesWhichCanProvideOrder(Id<Order, UUID> orderId,
                                                                  String storageView, String storageItemView) {
        return findStoragesWhichCanProvideOrder(orderId,
                viewRepository.getView(Storage.class, storageView),
                viewRepository.getView(StorageItem.class, storageItemView));
    }

    public List<StorageForOrder> findStoragesWhichCanProvideOrder(Id<Order, UUID> orderId,
                                                                  String storageView, View storageItemView) {
        return findStoragesWhichCanProvideOrder(orderId,
                viewRepository.getView(Storage.class, storageView), storageItemView);
    }

    public List<StorageForOrder> findStoragesWhichCanProvideOrder(Id<Order, UUID> orderId,
                                                                  View storageView, String storageItemView) {
        return findStoragesWhichCanProvideOrder(orderId, storageView,
                viewRepository.getView(StorageItem.class, storageItemView));
    }

    public List<StorageForOrder> findStoragesWhichCanProvideOrder(Id<Order, UUID> orderId,
                                                                  View storageView, View storageItemView) {
        CubaQueryFactory queryFactory = new CubaQueryFactory(txDm, metadata);

        View storageWithItems = ViewBuilder.of(StorageItem.class)
                .add("product", View.LOCAL)
                .add("storage", View.LOCAL)
                .addView(storageItemView)
                .build();

        QStorageItem storageItem = QStorageItem.storageItem;

        QOrderItem orderItem = QOrderItem.orderItem;

        List<StorageItem> appropriateStorageItems = queryFactory.select(storageItem)
                .from(storageItem, orderItem)
                .where(orderItem.product.id.eq(storageItem.product.id)
                        .and(orderItem.count.lt(storageItem.count))
                        .and(orderItem.order.id.eq(orderId.getValue())))
                .orderBy(storageItem.storage.updateTs.desc())
                .fetch(storageWithItems);

        Map<Storage, List<StorageItem>> appropriateStorages =
                appropriateStorageItems.stream()
                        .collect(groupingBy(StorageItem::getStorage));

        // post filtering
        View orderItemWithProduct = ViewBuilder.of(OrderItem.class).addView(View.LOCAL)
                .add("product", View.MINIMAL).build();

        List<OrderItem> orderItems = orderItemRepository
                .findOrderItemsByOrder(orderId, orderItemWithProduct);

        List<StorageForOrder> fineGrained = new ArrayList<>();
        for (Map.Entry<Storage, List<StorageItem>> es : appropriateStorages.entrySet()) {

            if (! doesStorageContainAllProducts(es.getValue(), orderItems)) {
                continue;
            }

            // reload storage with argument view
            Storage reloadedStorage = findStorageById(Id.of(es.getKey()), storageView);

            StorageForOrder storageForOrder = new StorageForOrder();
            storageForOrder.setAppropriateStorage(reloadedStorage);
            storageForOrder.setAppropriateStorageItems(es.getValue());

            fineGrained.add(storageForOrder);
        }


        return fineGrained;
    }

    @Nullable
    public Storage findStorageById(Id<Storage, UUID> id, String view) {
        return findStorageById(id, viewRepository.getView(Storage.class, view));
    }

    @Nullable
    public Storage findStorageById(Id<Storage, UUID> id, View view) {
        return txDm.load(id).view(view).optional().orElse(null);
    }

    private boolean doesStorageContainAllProducts(List<StorageItem> storageItems, List<OrderItem> orderItems) {
        Set<Product> orderProductItems = orderItems.stream()
                .map(OrderItem::getProduct).collect(toSet());

        Set<Product> storageProductItems = storageItems.stream()
                .map(StorageItem::getProduct).collect(toSet());

        return storageProductItems.containsAll(orderProductItems);
    }
}
