package com.store_inventory.exceptions;

public class CategoryNotFound extends RuntimeException{
    public CategoryNotFound(){
        super("Upss! The category was not found!");
    }

    public CategoryNotFound(String categoryName){
        super("Upss! The category " + categoryName + " was not found!");
    }
}
