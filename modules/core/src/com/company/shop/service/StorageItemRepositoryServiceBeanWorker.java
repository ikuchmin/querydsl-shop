package com.company.shop.service;

import com.company.shop.entity.OrderItem;
import com.company.shop.entity.Storage;
import com.company.shop.entity.StorageItem;
import com.haulmont.cuba.core.TransactionalDataManager;
import com.haulmont.cuba.core.entity.contracts.Id;
import com.haulmont.cuba.core.global.Metadata;
import org.springframework.stereotype.Component;
import ru.udya.querydsl.cuba.core.CubaQueryFactory;

import java.util.List;
import java.util.UUID;

@Component
public class StorageItemRepositoryServiceBeanWorker {

    TransactionalDataManager txDm;

    Metadata metadata;

    public StorageItemRepositoryServiceBeanWorker(TransactionalDataManager txDm, Metadata metadata) {
        this.txDm = txDm;
        this.metadata = metadata;
    }

    public List<StorageItem> findStorageItemsWhichCanProvideOrderItem(Id<Storage, UUID> storageId,
                                                                      Id<OrderItem, UUID> orderItemId) {

        CubaQueryFactory queryFactory = new CubaQueryFactory(txDm, metadata);

//        QStorageItem storageItem = QStorageItem.storageItem;
//        return queryFactory.selectFrom(storageItem)
//                .where(storageItem.storage.id.eq(storageId.getValue())
//                        .and(storageItem.product.id.eq(productId.getValue())))
//                .fetch();
//
        return null;
    }
}
