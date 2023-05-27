package com.store_inventory.repository;

import com.store_inventory.exceptions.ObjectNotFoundException;
import com.store_inventory.model.Product;
import com.store_inventory.model.enums.ProductType;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public sealed interface ProductRepository extends Repository<Product> permits ProductRepositoryImpl {
    Optional<Product> getObjectByName (String name) throws SQLException;
    List<Product> getAllProductsByCategoryId(UUID categoryId);
}
