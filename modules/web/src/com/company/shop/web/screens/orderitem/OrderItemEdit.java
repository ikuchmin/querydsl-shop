package com.company.shop.web.screens.orderitem;

import com.haulmont.cuba.gui.screen.*;
import com.company.shop.entity.OrderItem;

@UiController("shop_OrderItem.edit")
@UiDescriptor("order-item-edit.xml")
@EditedEntityContainer("orderItemDc")
@LoadDataBeforeShow
public class OrderItemEdit extends StandardEditor<OrderItem> {
}