package com.store_inventory.service.impl;

import com.store_inventory.model.Category;
import com.store_inventory.model.Location;
import com.store_inventory.model.Product;
import com.store_inventory.model.Stock;
import com.store_inventory.model.enums.LocationType;
import com.store_inventory.service.LocationService;

import java.util.*;
import java.util.stream.Collectors;

public class LocationServiceImpl implements LocationService {
    private static List<Location> locationList = new ArrayList<>();

    @Override
    public List<Location> getAllLocations() {
        return locationList;
    }

    @Override
    public Optional<Location> getLocationById(UUID id) {
        return locationList.stream().filter(l -> l.getId() == id).findAny();
    }

    @Override
    public Optional<Location> getLocationByName(String locationName) {
        return locationList.stream().filter(c -> Objects.equals(c.getName(), locationName)).findAny();
    }

    @Override
    public List<Location> getLocationsByType(LocationType lt) {
        return locationList.stream().filter(l -> l.getLocationType() == lt).collect(Collectors.toList());
    }


    @Override
    public void addLocation(Location l) {
        locationList.add(l);
    }

    @Override
    public void updateLocationById(UUID id, Location l) {
        this.removeLocationById(id);
        this.addLocation(l);
    }

    @Override
    public void removeLocationById(UUID id) {
        locationList.removeIf(l -> l.getId() == id);
    }

    @Override
    public void addStocksToLocation(UUID locationId, Stock s) {
        Optional<Location> l = this.getLocationById(locationId);
        if(l.isPresent()) {
            l.get().addStock(s);
        } else {
            System.out.println("Location with id " + locationId + " not found");
        }
    }

    @Override
    public void removeStocksFromLocation(UUID locationId, Stock s) {
        Optional<Location> l = this.getLocationById(locationId);
        if(l.isPresent()) {
            l.get().removeStock(s);
        } else {
            System.out.println("Location with id " + locationId + " not found");
        }
    }

    public Optional<Stock> getStockFromLocation(UUID locationId, UUID stockId){
        Optional<Location> l = this.getLocationById(locationId);
        if(l.isPresent()) {
            Optional<Stock> stock = l.get().getLocationStocks().stream().filter(s -> s.getId() == stockId).findAny();
            return stock;
        } else {
            System.out.println("Location with id " + locationId + " not found");
            return null;
        }
    }

    public void printAllStocks(){
        for(Location l: locationList) {
            System.out.println("Location " + l.getName() + "(" + l.getLocationType() + "):");
            for(Stock s: l.getLocationStocks()){
                System.out.println("-> " + s.getProduct().getName() + ": " + s.getProductQuantity());
            }
        }
    }

}
