package com.store_inventory.service.impl;

import com.store_inventory.model.Category;
import com.store_inventory.model.Order;
import com.store_inventory.model.abstracts.Transaction;
import com.store_inventory.service.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OrderServiceImpl implements OrderService {
    private static List<Order> orderList = new ArrayList<>();

    @Override
    public List<Order> getAllOrders() {
        return orderList;
    }

    @Override
    public Optional<Order> getOrderById(UUID id) {
        return orderList.stream().filter(o -> o.getId() == id).findFirst();
    }

    @Override
    public void addOrder(Order o) {
        orderList.add(o);
    }

    @Override
    public void updateOrderById(UUID id, Order o) {
        this.removeOrderById(id);
        this.addOrder(o);
    }

    @Override
    public void removeOrderById(UUID id) {
        orderList.removeIf(o -> o.getId() == id);
    }

    @Override
    public void updateOrderTransaction(UUID orderId, Transaction t) {
        Optional<Order> o = getOrderById(orderId);
        if (o.isPresent()) {
            o.get().setTransaction(t);
        } else {
            System.out.println("Order with id " + orderId + " not found");
        }
    }
}
