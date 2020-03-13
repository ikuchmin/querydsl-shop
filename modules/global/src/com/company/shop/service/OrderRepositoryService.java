package com.company.shop.service;

import com.company.shop.entity.Order;
import com.company.shop.entity.Storage;
import com.haulmont.cuba.core.entity.contracts.Id;
import com.haulmont.cuba.core.global.View;

import java.util.List;
import java.util.UUID;

public interface OrderRepositoryService {
    String NAME = "shop_OrderRepositoryService";

    Order findOrderByIdNN(Id<Order, UUID> orderId, String view);

    Order findOrderByIdNN(Id<Order, UUID> orderId, View view);

    List<Order> findCommittedOrdersByStorage(Id<Storage, UUID> storageId, String viewName);
}