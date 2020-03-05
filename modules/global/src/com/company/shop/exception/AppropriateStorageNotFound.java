package com.company.shop.exception;

import com.company.shop.entity.Order;
import com.haulmont.cuba.core.entity.contracts.Id;

import java.util.UUID;

public class AppropriateStorageNotFound extends RuntimeException {

    protected Id<Order, UUID> orderId;

    public AppropriateStorageNotFound(Id<Order, UUID> orderId) {
        this.orderId = orderId;
    }

    public AppropriateStorageNotFound(String message, Id<Order, UUID> orderId) {
        super(message);
        this.orderId = orderId;
    }

    public AppropriateStorageNotFound(String message, Throwable cause, Id<Order, UUID> orderId) {
        super(message, cause);
        this.orderId = orderId;
    }

    public AppropriateStorageNotFound(Throwable cause, Id<Order, UUID> orderId) {
        super(cause);
        this.orderId = orderId;
    }
}
