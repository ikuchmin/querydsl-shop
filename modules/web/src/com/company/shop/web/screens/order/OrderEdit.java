package com.company.shop.web.screens.order;

import com.haulmont.cuba.gui.screen.*;
import com.company.shop.entity.Order;

@UiController("shop_Order.edit")
@UiDescriptor("order-edit.xml")
@EditedEntityContainer("orderDc")
@LoadDataBeforeShow
public class OrderEdit extends StandardEditor<Order> {
}