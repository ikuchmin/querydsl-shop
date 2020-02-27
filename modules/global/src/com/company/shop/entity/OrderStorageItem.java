package com.company.shop.entity;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "SHOP_ORDER_STORAGE_ITEM")
@Entity(name = "shop_OrderStorageItem")
public class OrderStorageItem extends StandardEntity {
    private static final long serialVersionUID = - 8821430048248054428L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    protected Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STORAGE_ID")
    protected Storage storage;

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}