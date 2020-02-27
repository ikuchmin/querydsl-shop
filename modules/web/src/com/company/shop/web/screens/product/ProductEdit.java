package com.company.shop.web.screens.product;

import com.haulmont.cuba.gui.screen.*;
import com.company.shop.entity.Product;

@UiController("shop_Product.edit")
@UiDescriptor("product-edit.xml")
@EditedEntityContainer("productDc")
@LoadDataBeforeShow
public class ProductEdit extends StandardEditor<Product> {
}