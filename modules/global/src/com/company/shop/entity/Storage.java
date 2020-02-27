package com.company.shop.entity;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

@NamePattern("%s|name")
@Table(name = "SHOP_STORAGE")
@Entity(name = "shop_Storage")
public class Storage extends StandardEntity {
    private static final long serialVersionUID = 4790534225988659132L;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "storage")
    protected List<StorageItem> storageItems;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StorageItem> getStorageItems() {
        return storageItems;
    }

    public void setStorageItems(List<StorageItem> storageItems) {
        this.storageItems = storageItems;
    }
}