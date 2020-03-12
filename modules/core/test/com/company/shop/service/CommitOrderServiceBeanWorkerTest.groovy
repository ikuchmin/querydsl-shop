package com.company.shop.service

import com.company.shop.core.ShopIntegrationSpecification
import com.company.shop.entity.*
import com.haulmont.cuba.core.entity.contracts.Id
import com.haulmont.cuba.core.global.AppBeans
import com.haulmont.cuba.core.global.View

class CommitOrderServiceBeanWorkerTest extends ShopIntegrationSpecification {

    CommitOrderServiceBeanWorker delegate

    void setup() {
        delegate = AppBeans.get(CommitOrderServiceBeanWorker)
    }

    def "check that order can be committed"() {
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

        dataManager.commit(product1, product2,
                order1, orderItem1, orderItem2,
                storage1, storageItem1, storageItem2)

        when:
        def committedOrder = delegate.commitOrder(Id.of(order1), View.MINIMAL)

        then:
        committedOrder.orderStatus == OrderStatus.RESERVED
        (storageItem1.count - orderItem1.count) ==
                dataManager.load(Id.of(storageItem1))
                        .view(View.LOCAL).one().count

        (storageItem2.count - orderItem2.count) ==
                dataManager.load(Id.of(storageItem2))
                        .view(View.LOCAL).one().count


    }
}
