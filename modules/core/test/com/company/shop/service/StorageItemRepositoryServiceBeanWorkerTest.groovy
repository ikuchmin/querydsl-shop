package com.company.shop.service

import com.company.shop.core.ShopIntegrationSpecification
import com.company.shop.entity.Product
import com.company.shop.entity.Storage
import com.company.shop.entity.StorageItem
import com.haulmont.cuba.core.entity.contracts.Id
import com.haulmont.cuba.core.global.AppBeans

class StorageItemRepositoryServiceBeanWorkerTest extends ShopIntegrationSpecification {

    StorageItemRepositoryServiceBeanWorker delegate

    void setup() {
        delegate = AppBeans.get(StorageItemRepositoryServiceBeanWorker)
    }

    def "check that finding storage item by storage id and product id works as well"() {
        given:
        def product1 = metadata.create(Product)
        product1.name = 'test product'

        def storage1 = metadata.create(Storage)
        storage1.name = 'test storage1'

        def storageItem1 = metadata.create(StorageItem)
        storageItem1.storage = storage1
        storageItem1.product = product1
        storageItem1.count = 12

        def storage2 = metadata.create(Storage)
        storage2.name = 'test storage1'

        def storageItem2 = metadata.create(StorageItem)
        storageItem2.storage = storage2
        storageItem2.product = product1
        storageItem2.count = 2

        dataManager.commit(product1, storage1, storageItem1, storage2, storageItem2)

        when:
        def fetched = delegate.findStorageItemsWhichCanProvideOrderItem(Id.of(storage1), Id.of(product1))

        then:
        fetched == [storageItem1]
    }
}
