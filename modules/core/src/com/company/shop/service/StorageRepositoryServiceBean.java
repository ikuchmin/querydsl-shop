package com.company.shop.service;

import com.company.shop.entity.Order;
import com.haulmont.cuba.core.entity.contracts.Id;
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

       // "select storage from shop_Storage storage, shop_OrderItem orderItem inner join storage.storageItems as si where orderItem.product.id = si.product.id and orderItem.count < si.count";
        return storageRepositoryBeanWorker.findStoragesWhichCanProvideOrder(orderId, storageView, storageItemView);
    }
}