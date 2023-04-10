package com.store_inventory.exceptions;

public class StockNotFound extends RuntimeException{
    public StockNotFound(){
        super("Upss! The stock was not found!");
    }
}
