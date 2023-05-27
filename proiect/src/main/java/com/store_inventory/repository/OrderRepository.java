package com.store_inventory.repository;

import com.store_inventory.model.Order;

import java.util.Map;
import java.util.UUID;

public sealed interface OrderRepository extends Repository<Order> permits OrderRepositoryImpl{
    public void setOrderProducts(UUID orderId, Map<String, Integer> products);
    public void setOrderTransactionId(UUID orderId, UUID transactionId);
}
