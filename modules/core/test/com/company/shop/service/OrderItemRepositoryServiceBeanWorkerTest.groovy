package com.company.shop.service

import com.company.shop.core.ShopIntegrationSpecification
import com.company.shop.entity.Order
import com.company.shop.entity.OrderItem
import com.company.shop.entity.OrderStatus
import com.company.shop.entity.Product
import com.haulmont.cuba.core.entity.contracts.Id
import com.haulmont.cuba.core.global.AppBeans
import com.haulmont.cuba.core.global.View

class OrderItemRepositoryServiceBeanWorkerTest extends ShopIntegrationSpecification {

    OrderItemRepositoryServiceBeanWorker delegate

    @Override
    void setup() {
        delegate = AppBeans.get(OrderItemRepositoryServiceBeanWorker)
    }

    def "check that finding order items by order id works as well"() {
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

        dataManager.commit(product1, order1, orderItem1, order2, orderItem2)

        when:
        def fetched = delegate.findOrderItemsByOrder(Id.of(order1), View.MINIMAL)

        then:
        fetched == [orderItem1]
    }
}
