package com.company.shop.service;

import com.company.shop.entity.Order;
import com.company.shop.entity.OrderItem;
import com.company.shop.entity.QOrderItem;
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
public class OrderItemRepositoryServiceBeanWorker {

    protected TransactionalDataManager txDm;

    protected Metadata metadata;
    private ViewRepository viewRepository;

    public OrderItemRepositoryServiceBeanWorker(TransactionalDataManager txDm,
                                                Metadata metadata,
                                                ViewRepository viewRepository) {
        this.txDm = txDm;
        this.metadata = metadata;
        this.viewRepository = viewRepository;
    }

    public List<OrderItem> findOrderItemsByOrder(Id<Order, UUID> orderId, String view) {
        return findOrderItemsByOrder(orderId, viewRepository.getView(OrderItem.class, view));
    }

    public List<OrderItem> findOrderItemsByOrder(Id<Order, UUID> orderId, View view) {

        CubaQueryFactory queryFactory = new CubaQueryFactory(txDm, metadata);

        QOrderItem orderItem = QOrderItem.orderItem;

        return queryFactory.selectFrom(orderItem)
                .where(orderItem.order.id.eq(orderId.getValue()))
                .orderBy(orderItem.updateTs.desc())
                .fetch(view);
    }
}
