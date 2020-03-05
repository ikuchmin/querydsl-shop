package com.company.shop.service;

import com.company.shop.entity.Product;
import com.company.shop.entity.Storage;
import com.company.shop.entity.StorageItem;
import com.haulmont.cuba.core.entity.contracts.Id;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service(StorageItemRepositoryService.NAME)
public class StorageItemRepositoryServiceBean implements StorageItemRepositoryService {

    protected StorageItemRepositoryServiceBeanWorker storageItemRepositoryBeanWorker;

    public StorageItemRepositoryServiceBean(StorageItemRepositoryServiceBeanWorker storageItemRepositoryBeanWorker) {
        this.storageItemRepositoryBeanWorker = storageItemRepositoryBeanWorker;
    }

    @Override
    public List<StorageItem> findStorageItemsByStorageAndProduct(Id<Storage, UUID> storageId,
                                                                 Id<Product, UUID> productId) {

        return null;
//        return storageItemRepositoryBeanWorker.findStorageItemsWhichCanProvideOrderItem(storageId, productId);
    }
}