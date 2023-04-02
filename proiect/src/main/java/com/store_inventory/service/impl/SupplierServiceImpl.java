package com.store_inventory.service.impl;

import com.store_inventory.model.Supplier;
import com.store_inventory.service.SupplierService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SupplierServiceImpl implements SupplierService {

    private List<Supplier> supplierList = new ArrayList<>();

    @Override
    public List<Supplier> getAllSuppliers() {
        return supplierList;
    }

    @Override
    public Optional<Supplier> getSupplierById(UUID id) {
        return supplierList.stream().filter(s -> s.getId() == id).findFirst();
    }

    @Override
    public void addSupplier(Supplier s) {
        supplierList.add(s);
    }

    @Override
    public void updateSupplierById(UUID id, Supplier s) {
        this.removeSupplierById(id);
        this.addSupplier(s);
    }

    @Override
    public void removeSupplierById(UUID id) {
        supplierList.removeIf(c -> c.getId() == id);
    }
}
