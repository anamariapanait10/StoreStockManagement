package com.store_inventory.repository;

import com.store_inventory.model.Supplier;

import java.sql.SQLException;
import java.util.Optional;

public sealed interface SupplierRepository extends Repository<Supplier> permits SupplierRepositoryImpl{
    Optional<Supplier> getObjectByName (String name) throws SQLException;
}
