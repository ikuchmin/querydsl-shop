package com.company.shop.service;

import com.company.shop.entity.Product;
import com.company.shop.entity.Storage;
import com.company.shop.entity.StorageItem;
import com.haulmont.cuba.core.entity.contracts.Id;

import java.util.List;
import java.util.UUID;

public interface StorageItemRepositoryService {
    String NAME = "shop_StorageItemRepositoryService";

    List<StorageItem> findStorageItemsByStorageAndProduct(Id<Storage, UUID> storageId,
                                                          Id<Product, UUID> productId);
}