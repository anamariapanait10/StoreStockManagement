package com.store_inventory.service;

import com.store_inventory.model.CashTransaction;
import com.store_inventory.model.Order;
import com.store_inventory.model.Product;
import com.store_inventory.model.abstracts.Transaction;
import com.store_inventory.service.OrderService;

import java.util.*;

public final class OrderServiceImpl implements OrderService {
    private static List<Order> orderList = new ArrayList<>();
    private static LocationService locationService;
    private static SupplierService supplierService;
    private static TransactionService transactionService;

    public OrderServiceImpl(LocationService ls, SupplierService ss, TransactionService ts) {
        locationService = ls;
        supplierService = ss;
        transactionService = ts;
    }

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
            o.get().setTransactionId(t.getId());
        } else {
            System.out.println("Order with id " + orderId + " not found");
        }
    }

    public void printAllOrders(){
        for(Order o: orderList) {
            System.out.println("Order made by " + locationService.getLocationById(o.getOrderLocationId()).get().getName() +  " from supplier " + supplierService.getSupplierById(o.getSupplierId()).get().getSupplierName() + " with a total price of " + o.getTotalPrice() + ":");
            for(Map.Entry<Product, Integer> ord: o.getOrderedProducts().entrySet()){
                System.out.println("-> " + ord.getKey().getName() + ": " + ord.getValue());
            }
        }
    }

    public void printAllTransactions(){
        for(Order o: orderList) {

            System.out.print("Transaction with the total amount " + transactionService.getTransactionById(o.getTransactionId()).getAmount() + " was paid ");
            if(transactionService.getTransactionById(o.getTransactionId()) instanceof CashTransaction){
                System.out.println("with cash");
            } else {
                System.out.println("by card");
            }
        }
    }

}
