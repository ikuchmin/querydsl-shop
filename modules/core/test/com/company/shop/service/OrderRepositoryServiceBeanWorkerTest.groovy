package com.company.shop.service

import com.company.shop.core.ShopIntegrationSpecification
import com.company.shop.entity.*
import com.haulmont.cuba.core.entity.contracts.Id
import com.haulmont.cuba.core.global.AppBeans
import com.haulmont.cuba.core.global.View
import com.haulmont.cuba.core.global.ViewBuilder

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


    def "check that finding Order with Customer by id works as well"() {
        given:
        def newCustomer = metadata.create(Customer)
        newCustomer.with {
            firstName = 'Test'
            lastName = 'Testov'
        }
        dataManager.commit(newCustomer)

        def newOrder = metadata.create(Order)
        newOrder.with {
            number = '42AB452' + String.valueOf(Math.round(Math.random() * 1000))
            customer = newCustomer
        }
        dataManager.commit(newOrder)

        def orderWithCustomer = ViewBuilder.of(Order)
                .addView(View.MINIMAL).add("customer", View.MINIMAL)
                .build()

        when:
        def fetched = delegate.findOrderByIdNN(Id.of(newOrder), orderWithCustomer)

        then:
        newOrder == fetched
        newCustomer == fetched.customer
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
        def fetched = delegate.findCommittedOrdersByStorage(Id.of(storage), view)

        then:
        fetched == [order2]
    }
}
