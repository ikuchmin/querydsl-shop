package com.company.shop.service

import com.company.shop.core.ShopIntegrationSpecification
import com.company.shop.entity.Order
import com.haulmont.cuba.core.entity.contracts.Id
import com.haulmont.cuba.core.global.AppBeans
import com.haulmont.cuba.core.global.View

class OrderRepositoryServiceBeanWorkerTest extends ShopIntegrationSpecification {

    OrderRepositoryServiceBeanWorker delegate

    @Override
    void setup() {

         delegate = AppBeans.get(OrderRepositoryServiceBeanWorker)
    }

    def "check that finding Order by id working as well"() {
        given:
        def newOrder = metadata.create(Order)
        newOrder.number = '42AB452' + String.valueOf(Math.round(Math.random() * 1000))
        dataManager.commit(newOrder)

        when:
        def fetched = delegate.findOrderByIdNN(Id.of(newOrder), View.MINIMAL)

        then:
        newOrder == fetched

    }
}
