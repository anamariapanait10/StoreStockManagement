package com.store_inventory.exceptions;

import com.store_inventory.service.LocationServiceImpl;

public class LocationNotFound extends RuntimeException{
    public LocationNotFound(){
        super("Upss! The location was not found!");
    }
}
