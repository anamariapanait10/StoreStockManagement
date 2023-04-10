package com.store_inventory.exceptions;

public class SupplierNotFound extends RuntimeException {
    public SupplierNotFound(){
        super("Upss! The supplier was not found!");
    }
}
