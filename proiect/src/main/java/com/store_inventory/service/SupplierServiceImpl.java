package com.store_inventory.service;

import com.store_inventory.exceptions.CategoryNotFound;
import com.store_inventory.exceptions.SupplierNotFound;
import com.store_inventory.model.*;
import com.store_inventory.service.SupplierService;

import java.util.*;
import java.util.stream.Collectors;

public final class SupplierServiceImpl implements SupplierService {

    private static Map<UUID, Supplier> supplierMap = new HashMap<>();

    @Override
    public Map<UUID, Supplier> getAllSuppliers() {
        return supplierMap;
    }

    @Override
    public Optional<Supplier> getSupplierById(UUID id) {
        return supplierMap.values().stream().filter(s -> s.getId() == id).findAny();
    }

    @Override
    public Optional<Supplier> getSupplierByName(String supplierName) throws SupplierNotFound {
        Optional<Supplier> sup = supplierMap.values().stream().filter(s -> Objects.equals(s.getSupplierName(), supplierName)).findAny();
        if (!sup.isPresent()){
            throw new SupplierNotFound();
        }
        return sup;
    }

    @Override
    public void addSupplier(Supplier s) {
        supplierMap.put(s.getId(), s);
    }

    @Override
    public void updateSupplierById(UUID id, Supplier s) {
        this.removeSupplierById(id);
        this.addSupplier(s);
    }

    @Override
    public void removeSupplierById(UUID id) {
        supplierMap = supplierMap.entrySet().stream().filter(c -> !id.equals(c.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void printAllSuppliers(){
        for(Supplier s: supplierMap.values()) {
            System.out.println("Supplier " + s.getSupplierName());
        }
    }
}
