package com.company.shop.web.screens.customer;

import com.haulmont.cuba.gui.screen.*;
import com.company.shop.entity.Customer;

@UiController("shop_Customer.edit")
@UiDescriptor("customer-edit.xml")
@EditedEntityContainer("customerDc")
@LoadDataBeforeShow
public class CustomerEdit extends StandardEditor<Customer> {
}