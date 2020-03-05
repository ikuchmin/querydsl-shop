package com.company.shop.core

import com.company.shop.ShopTestContainer
import com.haulmont.cuba.core.global.AppBeans
import com.haulmont.cuba.core.global.DataManager
import com.haulmont.cuba.core.global.Metadata
import org.junit.ClassRule
import spock.lang.Shared
import spock.lang.Specification

class ShopIntegrationSpecification extends Specification {

    @ClassRule
    @Shared
    ShopTestContainer container = ShopTestContainer.Common.INSTANCE

    Metadata metadata
    DataManager dataManager

    void setup() {
        metadata = AppBeans.get(Metadata)
        dataManager = AppBeans.get(DataManager)
    }
}
