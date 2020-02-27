package com.company.shop.web.screens.order;

import com.company.shop.entity.Order;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.cuba.gui.screen.StandardLookup;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("shop_Order.browse")
@UiDescriptor("order-browse.xml")
@LookupComponent("ordersTable")
@LoadDataBeforeShow
public class OrderBrowse extends StandardLookup<Order> {

    @Subscribe("ordersTable.commitOrder")
    protected void onOrdersTableCommitOrder(Action.ActionPerformedEvent event) {

    }
}