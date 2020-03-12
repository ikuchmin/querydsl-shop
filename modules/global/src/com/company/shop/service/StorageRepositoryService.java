package com.company.shop.service;

import com.company.shop.entity.Order;
import com.company.shop.entity.Storage;
import com.company.shop.entity.StorageItem;
import com.haulmont.cuba.core.entity.contracts.Id;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public interface StorageRepositoryService {
    String NAME = "shop_StorageRepositoryService";

    List<StorageForOrder> findStoragesWhichCanProvideOrder(Id<Order, UUID> orderId,  String storageView, String storageItemView);

    class StorageForOrder implements Serializable {

        Storage appropriateStorage;

        List<StorageItem> appropriateStorageItems;

        public Storage getAppropriateStorage() {
            return appropriateStorage;
        }

        public void setAppropriateStorage(Storage appropriateStorage) {
            this.appropriateStorage = appropriateStorage;
        }

        public List<StorageItem> getAppropriateStorageItems() {
            return appropriateStorageItems;
        }

        public void setAppropriateStorageItems(List<StorageItem> appropriateStorageItems) {
            this.appropriateStorageItems = appropriateStorageItems;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            StorageForOrder that = (StorageForOrder) o;

            if (! appropriateStorage.equals(that.appropriateStorage)) return false;
            return appropriateStorageItems.equals(that.appropriateStorageItems);
        }

        @Override
        public int hashCode() {
            int result = appropriateStorage.hashCode();
            result = 31 * result + appropriateStorageItems.hashCode();
            return result;
        }
    }
}