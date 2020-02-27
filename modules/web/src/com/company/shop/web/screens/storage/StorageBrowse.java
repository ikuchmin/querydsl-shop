package com.company.shop.web.screens.storage;

import com.company.shop.entity.Storage;
import com.company.shop.web.screens.order.OrderCommittedByStorage;
import com.company.shop.web.screens.order.OrderCommittedByStorage.OrderCommittedByStorageParams;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.cuba.gui.screen.OpenMode;
import com.haulmont.cuba.gui.screen.StandardLookup;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import javax.inject.Inject;

@UiController("shop_Storage.browse")
@UiDescriptor("storage-browse.xml")
@LookupComponent("storagesTable")
@LoadDataBeforeShow
public class StorageBrowse extends StandardLookup<Storage> {

    @Inject
    protected ScreenBuilders screenBuilders;

    @Inject
    protected GroupTable<Storage> storagesTable;

    @Subscribe("storagesTable.showCommittedOrders")
    protected void onStoragesTableShowCommittedOrders(Action.ActionPerformedEvent event) {

        screenBuilders.screen(this)
                .withScreenClass(OrderCommittedByStorage.class)
                .withOptions(new OrderCommittedByStorageParams(
                        storagesTable.getSingleSelected()))
                .withOpenMode(OpenMode.DIALOG)
                .show();

    }
}