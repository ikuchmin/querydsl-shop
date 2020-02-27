package com.company.shop.web.screens.product;

import com.haulmont.cuba.gui.screen.*;
import com.company.shop.entity.Product;

@UiController("shop_Product.browse")
@UiDescriptor("product-browse.xml")
@LookupComponent("productsTable")
@LoadDataBeforeShow
public class ProductBrowse extends StandardLookup<Product> {
}