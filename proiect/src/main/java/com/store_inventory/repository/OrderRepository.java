package com.store_inventory.repository;

import com.store_inventory.model.Order;
public sealed interface OrderRepository extends Repository<Order> permits OrderRepositoryImpl{
}
