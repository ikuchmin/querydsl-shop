package com.company.shop.service;

import com.company.shop.entity.Order;
import com.company.shop.entity.QOrder;
import com.company.shop.entity.QOrderStorageItem;
import com.company.shop.entity.Storage;
import com.haulmont.cuba.core.TransactionalDataManager;
import com.haulmont.cuba.core.entity.contracts.Id;
import com.haulmont.cuba.core.global.Metadata;
import org.springframework.stereotype.Component;
import ru.udya.querydsl.cuba.core.CubaQueryFactory;

import java.util.List;
import java.util.UUID;

@Component
public class OrderRepositoryServiceBeanWorker {

    protected TransactionalDataManager txDm;

    protected Metadata metadata;

    public OrderRepositoryServiceBeanWorker(TransactionalDataManager txDm,
                                            Metadata metadata) {
        this.txDm = txDm;
        this.metadata = metadata;
    }

    public Order findOrderByIdNN(Id<Order, UUID> orderId, String viewName) {

        CubaQueryFactory queryFactory = new CubaQueryFactory(txDm, metadata);

        QOrder order = QOrder.order;
        Order resOrder = queryFactory
                .selectFrom(order)
                .where(order.id.eq(orderId.getValue()))
                .fetchOne();

        if (resOrder != null) {
            return resOrder;
        }

        throw new IllegalStateException("No results");
    }

    public List<Order> findCommittedOrdersByStorage(Id<Storage, UUID> storageId) {

        CubaQueryFactory queryFactory = new CubaQueryFactory(txDm, metadata);

        QOrder order = new QOrder("o");
        QOrderStorageItem orderStorageItem = QOrderStorageItem.orderStorageItem;

        return queryFactory.select(order)
                .from(orderStorageItem)
                .join(orderStorageItem.order, order)
                .where(orderStorageItem.storage.id.eq(storageId.getValue()))
                .fetch();
    }

}
