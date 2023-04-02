package com.store_inventory.service.impl;

import com.store_inventory.model.Category;
import com.store_inventory.model.Product;
import com.store_inventory.service.ProductService;

import java.util.*;

public class ProductServiceImpl implements ProductService {

    private static List<Product> productList = new ArrayList<>();

    @Override
    public List<Product> getAllProducts() {
        return productList;
    }

    @Override
    public Optional<Product> getProductById(UUID id) {
        return productList.stream().filter(p -> p.getId() == id).findFirst();
    }

    @Override
    public void addProduct(Product p) {
        productList.add(p);
    }

    @Override
    public void updateProductById(UUID id, Product p) {
        this.removeProductById(id);
        this.addProduct(p);
    }

    @Override
    public void removeProductById(UUID id) {
        productList.removeIf(p -> p.getId() == id);
    }

    public void printAllProducts(){
        for(Product p: productList) {
            System.out.println("Name: " + p.getName() + "\nPrice:" + p.getPrice() + "\nExpirationDate: " + p.getExpirationDate());
            for(Map.Entry<String, String> propr: p.getProperties().entrySet()){
                System.out.println(propr.getKey() + ": " + propr.getValue());
            }
            System.out.println("---------------------------------------------");
        }
    }
}
