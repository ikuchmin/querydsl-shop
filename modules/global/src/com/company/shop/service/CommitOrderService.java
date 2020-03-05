package com.company.shop.service;

import com.company.shop.entity.Order;
import com.haulmont.cuba.core.entity.contracts.Id;

import java.util.UUID;

public interface CommitOrderService {
    String NAME = "shop_CommitOrderService";

    Order commitOrder(Id<Order, UUID> orderId, String viewName);
}