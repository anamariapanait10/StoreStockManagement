package com.store_inventory.service;

import com.store_inventory.model.Order;
import com.store_inventory.model.Supplier;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public sealed interface SupplierService permits SupplierServiceImpl{
    Map<UUID, Supplier> getAllSuppliers();
    Optional<Supplier> getSupplierById(UUID id);

    Optional<Supplier> getSupplierByName(String supplierName);
    void addSupplier(Supplier s);
    void updateSupplierById(UUID id, Supplier s);
    void removeSupplierById(UUID id);

    void printAllSuppliers();

}
