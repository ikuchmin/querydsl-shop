package com.company.shop.service;

import com.company.shop.entity.Order;
import com.company.shop.entity.Storage;
import com.haulmont.cuba.core.entity.contracts.Id;
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
    public Order findOrderByIdNN(Id<Order, UUID> orderId, String viewName) {

        return repositoryBeanWorker.findOrderByIdNN(orderId, viewName);
    }

    @Override
    public List<Order> findCommittedOrdersByStorage(Id<Storage, UUID> storageId, String viewName) {
        return repositoryBeanWorker.findCommittedOrdersByStorage(storageId);
    }
}