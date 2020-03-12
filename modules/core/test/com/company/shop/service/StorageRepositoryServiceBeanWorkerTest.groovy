package com.company.shop.service

import com.company.shop.core.ShopIntegrationSpecification
import com.company.shop.entity.*
import com.haulmont.cuba.core.entity.contracts.Id
import com.haulmont.cuba.core.global.AppBeans
import com.haulmont.cuba.core.global.View

class StorageRepositoryServiceBeanWorkerTest extends ShopIntegrationSpecification {

    StorageRepositoryServiceBeanWorker delegate

    @Override
    void setup() {
        delegate = AppBeans.get(StorageRepositoryServiceBeanWorker)
    }

    def "check that finding appropriate storage for order with one item works as well"() {
        given:
        def product1 = metadata.create(Product)
        product1.name = 'test product'

        def order1 = metadata.create(Order)
        order1.number = '42AB452' + String.valueOf(Math.round(Math.random() * 1000))
        order1.orderStatus = OrderStatus.SUBMITTED

        def orderItem1 = metadata.create(OrderItem)
        orderItem1.order = order1
        orderItem1.product = product1
        orderItem1.count = 6

        def order2 = metadata.create(Order)
        order2.number = '42AB562' + String.valueOf(Math.round(Math.random() * 1000))
        order2.orderStatus = OrderStatus.SUBMITTED

        def orderItem2 = metadata.create(OrderItem)
        orderItem2.order = order2
        orderItem2.product = product1
        orderItem2.count = 1

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

        dataManager.commit(product1, order1, orderItem1, order2, orderItem2,
                storage1, storageItem1, storage2, storageItem2)

        when:
        def appropriateStorages = delegate
                .findStoragesWhichCanProvideOrder(Id.of(order1), View.MINIMAL, View.MINIMAL)

        then:
        appropriateStorages.collect({ it.appropriateStorage }) == [storage1]
        appropriateStorages.collect({ it.appropriateStorageItems }) == [[storageItem1]]

        when:
        appropriateStorages = delegate
                .findStoragesWhichCanProvideOrder(Id.of(order2), View.MINIMAL, View.MINIMAL)

        then:
        appropriateStorages.collect({ it.appropriateStorage })
                .containsAll([storage1, storage2])
        appropriateStorages.collect({ it.appropriateStorageItems }).flatten()
                .containsAll([[storageItem1], [storageItem2]].flatten())

    }

    def "check that finding appropriate storage for order with two items works as well"() {
        given:
        def product1 = metadata.create(Product)
        product1.name = 'test product1'

        def product2 = metadata.create(Product)
        product1.name = 'test product2'

        def order1 = metadata.create(Order)
        order1.number = '42AB452' + String.valueOf(Math.round(Math.random() * 1000))
        order1.orderStatus = OrderStatus.SUBMITTED

        def orderItem1 = metadata.create(OrderItem)
        orderItem1.order = order1
        orderItem1.product = product1
        orderItem1.count = 6

        def orderItem2 = metadata.create(OrderItem)
        orderItem2.order = order1
        orderItem2.product = product2
        orderItem2.count = 8

        def order2 = metadata.create(Order)
        order2.number = '42AB562' + String.valueOf(Math.round(Math.random() * 1000))
        order2.orderStatus = OrderStatus.SUBMITTED

        def orderItem3 = metadata.create(OrderItem)
        orderItem3.order = order2
        orderItem3.product = product1
        orderItem3.count = 1

        def orderItem4 = metadata.create(OrderItem)
        orderItem4.order = order2
        orderItem4.product = product2
        orderItem4.count = 1

        def storage1 = metadata.create(Storage)
        storage1.name = 'test storage1'

        def storageItem1 = metadata.create(StorageItem)
        storageItem1.storage = storage1
        storageItem1.product = product1
        storageItem1.count = 12

        def storageItem2 = metadata.create(StorageItem)
        storageItem2.storage = storage1
        storageItem2.product = product2
        storageItem2.count = 12

        def storage2 = metadata.create(Storage)
        storage2.name = 'test storage2'

        def storageItem3 = metadata.create(StorageItem)
        storageItem3.storage = storage2
        storageItem3.product = product1
        storageItem3.count = 2

        def storageItem4 = metadata.create(StorageItem)
        storageItem4.storage = storage2
        storageItem4.product = product2
        storageItem4.count = 12

        dataManager.commit(product1, product2,
                order1, orderItem1, orderItem2,
                order2, orderItem3, orderItem4,
                storage1, storageItem1, storageItem2,
                storage2, storageItem3, storageItem4)

        when:
        def appropriateStorages = delegate
                .findStoragesWhichCanProvideOrder(Id.of(order1), View.MINIMAL, View.MINIMAL)

        then:
        appropriateStorages.collect({ it.appropriateStorage }) == [storage1]
        appropriateStorages.collect({ it.appropriateStorageItems }).flatten()
                .containsAll([[storageItem1, storageItem2]].flatten())

        when:
        appropriateStorages = delegate
                .findStoragesWhichCanProvideOrder(Id.of(order2), View.MINIMAL, View.MINIMAL)

        then:
        appropriateStorages.collect({ it.appropriateStorage }).containsAll([storage1, storage2])
        appropriateStorages.collect({ it.appropriateStorageItems }).flatten()
                .containsAll([[storageItem1, storageItem2], [storageItem3, storageItem4]].flatten())
    }

    def "check that finding storage by id work as well"() {
        given:
        def storage1 = metadata.create(Storage)
        storage1.name = 'test storage1'
        dataManager.commit(storage1)

        when:
        def fetched = delegate.findStorageById(Id.of(storage1), View.MINIMAL)

        then:
        fetched == storage1
    }
}
