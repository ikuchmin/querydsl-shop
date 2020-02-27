package com.company.shop.web.screens.customer;

import com.haulmont.cuba.gui.screen.*;
import com.company.shop.entity.Customer;

@UiController("shop_Customer.browse")
@UiDescriptor("customer-browse.xml")
@LookupComponent("customersTable")
@LoadDataBeforeShow
public class CustomerBrowse extends StandardLookup<Customer> {
}