package com.company.shop.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum OrderStatus implements EnumClass<String> {

    SUBMITTED("A"),
    RESERVED("B");

    private String id;

    OrderStatus(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static OrderStatus fromId(String id) {
        for (OrderStatus at : OrderStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}