package com.company.shop.service;

import com.company.shop.entity.Order;
import com.company.shop.entity.QOrder;
import com.company.shop.entity.QOrderStorageItem;
import com.company.shop.entity.Storage;
import com.haulmont.cuba.core.TransactionalDataManager;
import com.haulmont.cuba.core.entity.contracts.Id;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewRepository;
import org.springframework.stereotype.Component;
import ru.udya.querydsl.cuba.core.CubaQueryFactory;

import java.util.List;
import java.util.UUID;

@Component
public class OrderRepositoryServiceBeanWorker {

    protected TransactionalDataManager txDm;

    protected Metadata metadata;

    protected ViewRepository viewRepository;

    public OrderRepositoryServiceBeanWorker(TransactionalDataManager txDm,
                                            Metadata metadata,
                                            ViewRepository viewRepository) {
        this.txDm = txDm;
        this.metadata = metadata;
        this.viewRepository = viewRepository;
    }

    public Order findOrderByIdNN(Id<Order, UUID> orderId, String viewName) {
        return findOrderByIdNN(orderId, viewRepository.getView(Order.class, viewName));
    }

    public Order findOrderByIdNN(Id<Order, UUID> orderId, View view) {

        CubaQueryFactory queryFactory = new CubaQueryFactory(txDm, metadata);

        QOrder order = QOrder.order;
        Order resOrder = queryFactory
                .selectFrom(order)
                .where(order.id.eq(orderId.getValue()))
                .fetchOne(view);

        if (resOrder != null) {
            return resOrder;
        }

        throw new IllegalStateException("No results");
    }

    public List<Order> findCommittedOrdersByStorage(Id<Storage, UUID> storageId, String view) {

        CubaQueryFactory queryFactory = new CubaQueryFactory(txDm, metadata);

        QOrder order = new QOrder("o");
        QOrderStorageItem orderStorageItem = QOrderStorageItem.orderStorageItem;

        return queryFactory.select(order)
                .from(orderStorageItem).join(orderStorageItem.order, order)
                .where(orderStorageItem.storage.id.eq(storageId.getValue()))
                .orderBy(order.updateTs.desc())
                .fetch(view);
    }

}
