package com.store_inventory.service;

import com.store_inventory.exceptions.ProductNotFound;
import com.store_inventory.model.Category;
import com.store_inventory.model.Product;
import com.store_inventory.service.ProductService;

import java.util.*;

public final class ProductServiceImpl implements ProductService {

    private static List<Product> productList = new ArrayList<>();

    @Override
    public List<Product> getAllProducts() {
        return productList;
    }

    @Override
    public Optional<Product> getProductById(UUID id) {
        return productList.stream().filter(p -> p.getId() == id).findAny();
    }

    @Override
    public Optional<Product> getProductByName(String productName) throws ProductNotFound {
        Optional<Product> p = productList.stream().filter(c -> Objects.equals(c.getName(), productName)).findAny();
        if (p.isEmpty()){
            throw new ProductNotFound();
        }
        return p;
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

    public void sortByProductName(){
        Collections.sort(productList);
    }
    public void printAllProducts(){
        int len = productList.size(), index = 0;
        for(Product p: productList) {
            System.out.println("Name: " + p.getName() + "\nPrice:" + p.getPrice() + "\nExpirationDate: " + p.getExpirationDate());
            for(Map.Entry<String, String> propr: p.getProperties().entrySet()){
                System.out.println(propr.getKey() + ": " + propr.getValue());
            }
            index++;
            if(index != len)
                System.out.println("---------------------------------------------");
        }
    }
}
