package com.company.shop.service;

import com.company.shop.entity.Order;
import com.company.shop.entity.Storage;
import com.haulmont.cuba.core.entity.contracts.Id;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service(OrderRepositoryService.NAME)
public class OrderRepositoryServiceBean implements OrderRepositoryService {

    @Override
    public Order findOrderByIdNN(Id<Order, UUID> orderId, String viewName) {

    };

    @Override
    public List<Order> findCommittedOrdersByStorage(Id<Storage, UUID> storageId, String viewName) {
        return null;
    }
}