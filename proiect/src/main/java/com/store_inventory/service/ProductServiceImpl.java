package com.store_inventory.service;

import com.store_inventory.exceptions.ProductNotFound;
import com.store_inventory.model.Product;
import com.store_inventory.repository.ProductRepository;
import com.store_inventory.repository.ProductRepositoryImpl;

import java.sql.SQLException;
import java.util.*;

public final class ProductServiceImpl implements ProductService {

    private static final ProductRepository productRepository = new ProductRepositoryImpl();

    @Override
    public List<Product> getAllProducts() {
        return productRepository.getAll();
    }

    @Override
    public Optional<Product> getProductById(UUID id) {
        return productRepository.getAll().stream().filter(p -> Objects.equals(p.getId(), id)).findAny();
    }

    @Override
    public Optional<Product> getProductByName(String productName) throws ProductNotFound {
        Optional<Product> p = productRepository.getAll().stream().filter(c -> Objects.equals(c.getName(), productName)).findAny();
        if (p.isEmpty()){
            throw new ProductNotFound();
        }
        return p;
    }
    @Override
    public void addProduct(Product p) {
        try {
            productRepository.addNewObject(p);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateProductById(UUID id, Product p) {
        this.removeProductById(id);
        this.addProduct(p);
    }

    @Override
    public void removeProductById(UUID id) {
        productRepository.deleteObjectById(id);
    }

    public void sortByProductName(){
        Collections.sort(productRepository.getAll());
    }
    public void printAllProducts(){
        int len = productRepository.getAll().size(), index = 0;
        for(Product p: productRepository.getAll()) {
            System.out.println("Name: " + p.getName() + "\nPrice:" + p.getPrice() + "\nExpirationDate: " + p.getExpirationDate());
            index++;
            if(index != len)
                System.out.println("---------------------------------------------");
        }
    }

    public List<Product> getAllProductsByCategoryId(UUID category_id){
        List<Product> productList = productRepository.getAllProductsByCategoryId(category_id);

        Iterator<Product> productIterator = productList.iterator();
        while (productIterator.hasNext()) {
            Product product = productIterator.next();
            product.setPrice(product.getPrice()+1);
        }
        return productList;
    }
}
