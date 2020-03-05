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

    def "check that finding appropriate for order storage works as well"() {
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
        def appropriateOrders = delegate
                .findStoragesWhichCanProvideOrder(Id.of(order1), View.MINIMAL)

        then:
        appropriateOrders == [storage1]

        when:
        appropriateOrders = delegate
                .findStoragesWhichCanProvideOrder(Id.of(order2), View.MINIMAL)

        then:
        appropriateOrders == [storage1, storage2]


    }
}
