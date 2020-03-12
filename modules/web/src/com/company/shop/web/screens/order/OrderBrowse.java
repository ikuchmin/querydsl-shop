package com.company.shop.web.screens.order;

import com.company.shop.entity.Order;
import com.company.shop.service.CommitOrderService;
import com.haulmont.cuba.core.entity.contracts.Id;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.cuba.gui.screen.StandardLookup;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import javax.inject.Inject;

@UiController("shop_Order.browse")
@UiDescriptor("order-browse.xml")
@LookupComponent("ordersTable")
@LoadDataBeforeShow
public class OrderBrowse extends StandardLookup<Order> {

    @Inject
    protected CollectionContainer<Order> ordersDc;

    @Inject
    protected GroupTable<Order> ordersTable;

    @Inject
    protected CommitOrderService commitOrderService;

    @Subscribe("ordersTable.commitOrder")
    protected void onOrdersTableCommitOrder(Action.ActionPerformedEvent event) {
        Order commitOrder = commitOrderService
                .commitOrder(Id.of(ordersTable.getSingleSelected()), ordersDc.getView());

        ordersDc.replaceItem(commitOrder);

    }
}