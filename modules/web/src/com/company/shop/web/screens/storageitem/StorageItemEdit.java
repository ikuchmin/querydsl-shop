package com.company.shop.web.screens.storageitem;

import com.haulmont.cuba.gui.screen.*;
import com.company.shop.entity.StorageItem;

@UiController("shop_StorageItem.edit")
@UiDescriptor("storage-item-edit.xml")
@EditedEntityContainer("storageItemDc")
@LoadDataBeforeShow
public class StorageItemEdit extends StandardEditor<StorageItem> {
}