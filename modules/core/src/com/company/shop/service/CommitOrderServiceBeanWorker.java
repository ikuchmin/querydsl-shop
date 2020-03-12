package com.company.shop.service;

import com.company.shop.entity.Order;
import com.company.shop.entity.OrderItem;
import com.company.shop.entity.OrderStatus;
import com.company.shop.entity.OrderStorageItem;
import com.company.shop.entity.Storage;
import com.company.shop.exception.AppropriateStorageNotFound;
import com.haulmont.cuba.core.TransactionalDataManager;
import com.haulmont.cuba.core.entity.contracts.Id;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
public class CommitOrderServiceBeanWorker {

    protected TransactionalDataManager txDm;

    protected Metadata metadata;

    protected OrderRepositoryServiceBeanWorker orderRepository;

    protected StorageRepositoryServiceBeanWorker storageRepository;

    protected OrderItemRepositoryServiceBeanWorker orderItemRepository;

    public Order commitOrder(Id<Order, UUID> orderId, String viewName) {

        // todo resolve
        List<Storage> appropriateStorages = Collections.emptyList();
                //storageRepository
                // .findStoragesWhichCanProvideOrder(orderId, viewName, viewName);

        if (appropriateStorages.isEmpty()) {
            throw new AppropriateStorageNotFound(orderId);
        }

        Storage selectedStorage = appropriateStorages.get(0);

        CommitContext cc = new CommitContext();

        Order order = orderRepository.findOrderByIdNN(orderId, viewName);
        order.setOrderStatus(OrderStatus.SUBMITTED);
        cc.addInstanceToCommit(order);

        List<OrderItem> orderItemsByOrder =
                orderItemRepository.findOrderItemsByOrder(orderId, View.LOCAL);

        for (OrderItem orderItem : orderItemsByOrder) {

            //cc.addInstanceToCommit(storageItem);

        }

        OrderStorageItem orderStorageItem = metadata.create(OrderStorageItem.class);
        orderStorageItem.setOrder(order);
        orderStorageItem.setStorage(selectedStorage);


        return null;
    }
}
