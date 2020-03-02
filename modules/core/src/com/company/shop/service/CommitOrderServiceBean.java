package com.company.shop.service;

import com.company.shop.entity.Order;
import com.haulmont.cuba.core.entity.contracts.Id;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service(CommitOrderService.NAME)
public class CommitOrderServiceBean implements CommitOrderService {

    @Override
    public Order commitOrder(Id<Order, UUID> orderId) {

        return null;
    }
}