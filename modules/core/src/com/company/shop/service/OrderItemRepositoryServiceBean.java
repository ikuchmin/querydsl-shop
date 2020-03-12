package com.company.shop.service;

import com.company.shop.entity.Order;
import com.company.shop.entity.OrderItem;
import com.haulmont.cuba.core.entity.contracts.Id;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service(OrderItemRepositoryService.NAME)
public class OrderItemRepositoryServiceBean implements OrderItemRepositoryService {

    protected OrderItemRepositoryServiceBeanWorker orderItemRepositoryServiceBeanWorker;

    @Override
    public List<OrderItem> findOrderItemsByOrder(Id<Order, UUID> orderId) {
        return orderItemRepositoryServiceBeanWorker.findOrderItemsByOrder(orderId, View.LOCAL);
    }
}