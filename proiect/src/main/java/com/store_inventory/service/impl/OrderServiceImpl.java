package com.store_inventory.service.impl;

import com.store_inventory.model.CashTransaction;
import com.store_inventory.model.Order;
import com.store_inventory.model.Product;
import com.store_inventory.model.abstracts.Transaction;
import com.store_inventory.service.OrderService;

import java.util.*;

public class OrderServiceImpl implements OrderService {
    private static List<Order> orderList = new ArrayList<>();

    @Override
    public List<Order> getAllOrders() {
        return orderList;
    }

    @Override
    public Optional<Order> getOrderById(UUID id) {
        return orderList.stream().filter(o -> o.getId() == id).findAny();
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

    public void printAllOrders(){
        for(Order o: orderList) {
            System.out.println("Order made by " + o.getOrderLocation().getName() +  " from supplier " + o.getSupplier().getSupplierName() + " with a total price of " + o.getTotalPrice() + ":");
            for(Map.Entry<Product, Integer> ord: o.getOrderedProducts().entrySet()){
                System.out.println("-> " + ord.getKey().getName() + ": " + ord.getValue());
            }
        }
    }

    public void printAllTransactions(){
        for(Order o: orderList) {
            System.out.print("Transaction with the total amout " + o.getTransaction().getAmount() + " was paid ");
            if(o.getTransaction() instanceof CashTransaction){
                System.out.println("with cash");
            } else {
                System.out.println("by card");
            }
        }
    }

}
