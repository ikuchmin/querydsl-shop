package com.company.shop.service;

import com.company.shop.entity.Order;
import com.company.shop.entity.Storage;
import com.haulmont.cuba.core.entity.contracts.Id;

import java.util.List;
import java.util.UUID;

public interface OrderRepositoryService {
    String NAME = "shop_OrderRepositoryService";

    Order findOrderByIdNN(Id<Order, UUID> orderId, String viewName);

    List<Order> findCommittedOrdersByStorage(Id<Storage, UUID> storageId, String viewName);
}