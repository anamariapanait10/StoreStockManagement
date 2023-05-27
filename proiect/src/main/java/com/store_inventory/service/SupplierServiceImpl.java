package com.store_inventory.service;

import com.store_inventory.exceptions.SupplierNotFound;
import com.store_inventory.model.*;
import com.store_inventory.repository.SupplierRepository;
import com.store_inventory.repository.SupplierRepositoryImpl;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public final class SupplierServiceImpl implements SupplierService {

    private static final SupplierRepository supplierRepository = new SupplierRepositoryImpl();

    @Override
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.getAll();
    }

    @Override
    public Optional<Supplier> getSupplierById(UUID id) {
        return supplierRepository.getAll().stream().filter(s -> Objects.equals(s.getId(), id)).findAny();
    }

    @Override
    public Optional<Supplier> getSupplierByName(String supplierName) throws SupplierNotFound {
        Optional<Supplier> sup = supplierRepository.getAll().stream().filter(s -> Objects.equals(s.getSupplierName(), supplierName)).findAny();
        if (!sup.isPresent()){
            throw new SupplierNotFound();
        }
        return sup;
    }

    @Override
    public void addSupplier(Supplier s) {
        try {
            supplierRepository.addNewObject(s);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateSupplierById(UUID id, Supplier s) {
        this.removeSupplierById(id);
        this.addSupplier(s);
    }

    @Override
    public void removeSupplierById(UUID id) {
        supplierRepository.deleteObjectById(id);
    }

    public void printAllSuppliers(){
        for(Supplier s: supplierRepository.getAll()) {
            System.out.println("Supplier " + s.getSupplierName());
        }
    }
}
