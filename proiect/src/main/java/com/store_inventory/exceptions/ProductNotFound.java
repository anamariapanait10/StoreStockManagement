package com.store_inventory.exceptions;

public class ProductNotFound extends RuntimeException{
    public ProductNotFound(){
        super("Upss! The product was not found!");
    }
}
