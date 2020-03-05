package com.company.shop.service

import com.company.shop.core.ShopIntegrationSpecification
import com.company.shop.entity.Order
import com.company.shop.entity.OrderStatus
import com.company.shop.entity.OrderStorageItem
import com.company.shop.entity.Storage
import com.haulmont.cuba.core.entity.contracts.Id
import com.haulmont.cuba.core.global.AppBeans
import com.haulmont.cuba.core.global.View

class OrderRepositoryServiceBeanWorkerTest extends ShopIntegrationSpecification {

    OrderRepositoryServiceBeanWorker delegate

    @Override
    void setup() {

         delegate = AppBeans.get(OrderRepositoryServiceBeanWorker)
    }

    def "check that finding Order by id works as well"() {
        given:
        def newOrder = metadata.create(Order)
        newOrder.number = '42AB452' + String.valueOf(Math.round(Math.random() * 1000))
        dataManager.commit(newOrder)

        when:
        def fetched = delegate.findOrderByIdNN(Id.of(newOrder), View.MINIMAL)

        then:
        newOrder == fetched
    }


    def "check that finding committed orders by storage id works as well"() {
        given:
        def order1 = metadata.create(Order)
        order1.number = '42AB452' + String.valueOf(Math.round(Math.random() * 1000))
        order1.orderStatus = OrderStatus.SUBMITTED

        def order2 = metadata.create(Order)
        order2.number = '42AB672' + String.valueOf(Math.round(Math.random() * 1000))
        order2.orderStatus = OrderStatus.RESERVED

        def storage = metadata.create(Storage)
        storage.name = 'Test storage'

        def orderStorageItem = metadata.create(OrderStorageItem)
        orderStorageItem.order = order2
        orderStorageItem.storage = storage

        dataManager.commit(order1, order2, storage, orderStorageItem)

        when:
        def fetched = delegate.findCommittedOrdersByStorage(Id.of(storage))

        then:
        fetched == [order2]
    }
}
