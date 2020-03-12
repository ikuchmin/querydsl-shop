package com.company.shop.service;

import com.company.shop.entity.Order;
import com.haulmont.cuba.core.entity.contracts.Id;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service(CommitOrderService.NAME)
public class CommitOrderServiceBean implements CommitOrderService {

    protected CommitOrderServiceBeanWorker commitOrderServiceBeanWorker;

    public CommitOrderServiceBean(CommitOrderServiceBeanWorker commitOrderServiceBeanWorker) {
        this.commitOrderServiceBeanWorker = commitOrderServiceBeanWorker;
    }

    @Override
    public Order commitOrder(Id<Order, UUID> orderId, String viewName) {
        return commitOrderServiceBeanWorker.commitOrder(orderId, viewName);
    }

    @Override
    public Order commitOrder(Id<Order, UUID> orderId, View view) {
        return commitOrderServiceBeanWorker.commitOrder(orderId, view);
    }
}