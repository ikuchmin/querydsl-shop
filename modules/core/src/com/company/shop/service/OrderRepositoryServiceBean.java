package com.company.shop.service;

import com.company.shop.entity.Order;
import com.company.shop.entity.Storage;
import com.haulmont.cuba.core.entity.contracts.Id;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service(OrderRepositoryService.NAME)
public class OrderRepositoryServiceBean implements OrderRepositoryService {

    protected OrderRepositoryServiceBeanWorker repositoryBeanWorker;

    public OrderRepositoryServiceBean(OrderRepositoryServiceBeanWorker repositoryBeanWorker) {
        this.repositoryBeanWorker = repositoryBeanWorker;
    }

    @Override
    public Order findOrderByIdNN(Id<Order, UUID> orderId, String view) {

        return repositoryBeanWorker.findOrderByIdNN(orderId, view);
    }

    @Override
    public Order findOrderByIdNN(Id<Order, UUID> orderId, View view) {
        return repositoryBeanWorker.findOrderByIdNN(orderId, view);
    }

    @Override
    public List<Order> findCommittedOrdersByStorage(Id<Storage, UUID> storageId, String view) {
        return repositoryBeanWorker.findCommittedOrdersByStorage(storageId, view);
    }
}