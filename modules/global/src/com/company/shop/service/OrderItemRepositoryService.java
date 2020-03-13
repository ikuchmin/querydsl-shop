package com.company.shop.service;

import com.company.shop.entity.Order;
import com.company.shop.entity.OrderItem;
import com.haulmont.cuba.core.entity.contracts.Id;
import com.haulmont.cuba.core.global.View;

import java.util.List;
import java.util.UUID;

public interface OrderItemRepositoryService {
    String NAME = "shop_OrderItemRepositoryService";

    List<OrderItem> findOrderItemsByOrder(Id<Order, UUID> orderId, String view);

    List<OrderItem> findOrderItemsByOrder(Id<Order, UUID> orderId, View view);
}