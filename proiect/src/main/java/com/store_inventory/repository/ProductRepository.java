package com.store_inventory.repository;

import com.store_inventory.exceptions.ObjectNotFoundException;
import com.store_inventory.model.Product;
import com.store_inventory.model.enums.ProductType;

import java.util.Optional;

public sealed interface ProductRepository extends Repository<Product> permits ProductRepositoryImpl {
}
