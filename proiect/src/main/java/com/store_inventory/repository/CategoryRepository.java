package com.store_inventory.repository;

import com.store_inventory.model.Category;

import java.sql.SQLException;
import java.util.Optional;
public sealed interface CategoryRepository extends Repository<Category> permits CategoryRepositoryImpl{
    Optional<Category> getObjectByName (String name) throws SQLException;
}
