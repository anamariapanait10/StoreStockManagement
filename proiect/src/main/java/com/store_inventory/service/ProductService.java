package com.store_inventory.service;

import com.store_inventory.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    List<Product> getAllProducts();
    Optional<Product> getProductById(UUID id);
    void addProduct(Product p);
    void updateProductById(UUID id, Product p);
    void removeProductById(UUID id);

    void printAllProducts();
}
