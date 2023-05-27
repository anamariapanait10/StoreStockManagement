package com.store_inventory.service;

import com.store_inventory.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public sealed interface ProductService permits ProductServiceImpl{
    List<Product> getAllProducts();
    Optional<Product> getProductById(UUID id);
    Optional<Product> getProductByName(String productName);
    void addProduct(Product p);
    void updateProductById(UUID id, Product p);
    void removeProductById(UUID id);
    void sortByProductName();
    void printAllProducts();
    List<Product> getAllProductsByCategoryId(UUID category_id);
}
