package com.company.shop.service;

import com.company.shop.entity.Order;
import com.company.shop.entity.Storage;
import com.haulmont.cuba.core.entity.contracts.Id;

import java.util.List;
import java.util.UUID;

public interface StorageRepositoryService {
    String NAME = "shop_StorageRepositoryService";

    List<Storage> findStoragesWhichCanProvideOrder(Id<Order, UUID> orderId, String viewName);
}