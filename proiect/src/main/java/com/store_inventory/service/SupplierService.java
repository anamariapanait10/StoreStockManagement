package com.store_inventory.service;

import com.store_inventory.model.Order;
import com.store_inventory.model.Supplier;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SupplierService {

    List<Supplier> getAllSuppliers();
    Optional<Supplier> getSupplierById(UUID id);
    void addSupplier(Supplier s);
    void updateSupplierById(UUID id, Supplier s);
    void removeSupplierById(UUID id);

}
