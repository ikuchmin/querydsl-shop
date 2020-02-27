package com.company.shop.web.screens.order;

import com.company.shop.entity.Order;
import com.company.shop.entity.Storage;
import com.company.shop.service.OrderRepositoryService;
import com.haulmont.cuba.core.entity.contracts.Id;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.cuba.gui.screen.ScreenOptions;
import com.haulmont.cuba.gui.screen.StandardLookup;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import javax.inject.Inject;
import java.util.List;

@UiController("shop_OrderCommittedByStorage")
@UiDescriptor("order-committed-by-storage.xml")
@LookupComponent("ordersTable")
public class OrderCommittedByStorage extends StandardLookup<Order> {

    @Inject
    protected OrderRepositoryService orderRepositoryService;

    @Inject
    protected CollectionContainer<Order> ordersDc;

    private Storage storage;

    @Subscribe
    protected void onInit(InitEvent event) {
        ScreenOptions options = event.getOptions();

        if (options instanceof OrderCommittedByStorageParams) {
            storage = ((OrderCommittedByStorageParams) options).getStorage();
        }
    }

    @Subscribe
    protected void onAfterInit(AfterInitEvent event) {
        List<Order> orders = orderRepositoryService.findCommittedOrdersByStorage(
                Id.of(storage), "order-commited-view");

        ordersDc.setItems(orders);
    }

    public static class OrderCommittedByStorageParams implements ScreenOptions {

        private Storage storage;

        public OrderCommittedByStorageParams(Storage storage) {
            this.storage = storage;
        }

        public Storage getStorage() {
            return storage;
        }
    }
}