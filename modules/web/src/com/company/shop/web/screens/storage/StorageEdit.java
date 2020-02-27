package com.company.shop.web.screens.storage;

import com.haulmont.cuba.gui.screen.*;
import com.company.shop.entity.Storage;

@UiController("shop_Storage.edit")
@UiDescriptor("storage-edit.xml")
@EditedEntityContainer("storageDc")
@LoadDataBeforeShow
public class StorageEdit extends StandardEditor<Storage> {
}