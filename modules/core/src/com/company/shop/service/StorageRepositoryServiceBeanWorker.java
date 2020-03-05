package com.company.shop.service;

import com.company.shop.entity.Order;
import com.company.shop.entity.QOrderItem;
import com.company.shop.entity.QStorage;
import com.company.shop.entity.QStorageItem;
import com.company.shop.entity.Storage;
import com.haulmont.cuba.core.TransactionalDataManager;
import com.haulmont.cuba.core.entity.contracts.Id;
import com.haulmont.cuba.core.global.Metadata;
import org.springframework.stereotype.Component;
import ru.udya.querydsl.cuba.core.CubaQueryFactory;

import java.util.List;
import java.util.UUID;

@Component
public class StorageRepositoryServiceBeanWorker {

    protected TransactionalDataManager txDm;

    protected Metadata metadata;

    public StorageRepositoryServiceBeanWorker(TransactionalDataManager txDm, Metadata metadata) {
        this.txDm = txDm;
        this.metadata = metadata;
    }

    public List<Storage> findStoragesWhichCanProvideOrder(Id<Order, UUID> orderId, String viewName) {
        CubaQueryFactory queryFactory = new CubaQueryFactory(txDm, metadata);

        QStorage storage = QStorage.storage;
        QStorageItem storageItem = new QStorageItem("si");
        QOrderItem orderItem = QOrderItem.orderItem;

        return queryFactory.select(storage)
                .from(storage, orderItem)
                .join(storage.storageItems, storageItem)
                .where(orderItem.product.id.eq(storageItem.product.id)
                        .and(orderItem.count.lt(storageItem.count))
                        .and(orderItem.order.id.eq(orderId.getValue())))
                .fetch();
    }
}
