package com.company.shop.entity;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.annotation.PostConstruct;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Table(name = "SHOP_ORDER")
@Entity(name = "shop_Order")
public class Order extends StandardEntity {
    private static final long serialVersionUID = - 5613009577548209542L;


    @Pattern(message = "{msg://shop_Order.number.validation.Pattern}", regexp = "[0-9]{2}-[A-Z]{8}")
    @NotNull
    @Column(name = "NUMBER_", nullable = false, unique = true)
    protected String number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    protected Customer customer;

    @NotNull
    @Column(name = "ORDER_STATUS", nullable = false)
    protected String orderStatus;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "order")
    protected List<OrderItem> orderItems;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public OrderStatus getOrderStatus() {
        return orderStatus == null ? null : OrderStatus.fromId(orderStatus);
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus == null ? null : orderStatus.getId();
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @PostConstruct
    public void postConstruct() {
        this.setOrderStatus(OrderStatus.SUBMITTED);
    }
}