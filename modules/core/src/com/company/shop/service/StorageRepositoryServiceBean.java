package com.company.shop.service;

import com.company.shop.entity.Order;
import com.haulmont.cuba.core.entity.contracts.Id;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service(StorageRepositoryService.NAME)
public class StorageRepositoryServiceBean implements StorageRepositoryService {

    protected StorageRepositoryServiceBeanWorker storageRepositoryBeanWorker;

    public StorageRepositoryServiceBean(StorageRepositoryServiceBeanWorker storageRepositoryBeanWorker) {
        this.storageRepositoryBeanWorker = storageRepositoryBeanWorker;
    }

    @Override
    public List<StorageForOrder> findStoragesWhichCanProvideOrder(Id<Order, UUID> orderId, String storageView, String storageItemView) {

        return storageRepositoryBeanWorker.findStoragesWhichCanProvideOrder(orderId, storageView, storageItemView);
    }

    @Override
    public List<StorageForOrder> findStoragesWhichCanProvideOrder(Id<Order, UUID> orderId, View storageView, String storageItemView) {
        return storageRepositoryBeanWorker.findStoragesWhichCanProvideOrder(orderId, storageView, storageItemView);
    }

    @Override
    public List<StorageForOrder> findStoragesWhichCanProvideOrder(Id<Order, UUID> orderId, String storageView, View storageItemView) {
        return storageRepositoryBeanWorker.findStoragesWhichCanProvideOrder(orderId, storageView, storageItemView);
    }

    @Override
    public List<StorageForOrder> findStoragesWhichCanProvideOrder(Id<Order, UUID> orderId, View storageView, View storageItemView) {
        return storageRepositoryBeanWorker.findStoragesWhichCanProvideOrder(orderId, storageView, storageItemView);
    }
}