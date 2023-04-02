package com.store_inventory.service;

import com.store_inventory.model.Location;
import com.store_inventory.model.Order;
import com.store_inventory.model.abstracts.Transaction;
import com.store_inventory.model.enums.LocationType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {

    List<Order> getAllOrders();
    Optional<Order> getOrderById(UUID id);
    void addOrder(Order o);
    void updateOrderById(UUID id, Order o);
    void removeOrderById(UUID id);
    void updateOrderTransaction(UUID orderId, Transaction t);

    void printAllOrders();
}
