package com.company.shop.entity;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "SHOP_STORAGE_ITEM")
@Entity(name = "shop_StorageItem")
public class StorageItem extends StandardEntity {
    private static final long serialVersionUID = 1672636837712751099L;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRODUCT_ID")
    protected Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STORAGE_ID")
    protected Storage storage;

    @NotNull
    @Column(name = "COUNT_", nullable = false)
    protected Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }
}