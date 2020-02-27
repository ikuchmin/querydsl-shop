package com.company.shop.web.screens.storage;

import com.haulmont.cuba.gui.screen.*;
import com.company.shop.entity.Storage;

@UiController("shop_Storage.browse")
@UiDescriptor("storage-browse.xml")
@LookupComponent("storagesTable")
@LoadDataBeforeShow
public class StorageBrowse extends StandardLookup<Storage> {
}