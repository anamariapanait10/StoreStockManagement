package com.store_inventory.service;

import com.store_inventory.exceptions.LocationNotFound;
import com.store_inventory.exceptions.StockNotFound;
import com.store_inventory.model.*;
import com.store_inventory.model.enums.LocationType;


import java.util.*;
import java.util.stream.Collectors;

public final class LocationServiceImpl implements LocationService {
    private static List<Location> locationList = new ArrayList<>();

    private static ProductService productService;


    public LocationServiceImpl(ProductService productService){
        LocationServiceImpl.productService = productService;
    }
    @Override
    public List<Location> getAllLocations() {
        return locationList;
    }

    @Override
    public Optional<Location> getLocationById(UUID id) {
        return locationList.stream().filter(l -> l.getId() == id).findAny();
    }

    @Override
    public Optional<Location> getLocationByName(String locationName) throws LocationNotFound {
        Optional<Location> loc = locationList.stream().filter(l -> Objects.equals(l.getName(), locationName)).findAny();
        if (!loc.isPresent()){
            throw new LocationNotFound();
        }
        return loc;
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

    public Optional<Stock> getStockFromLocation(UUID locationId, UUID stockId) throws StockNotFound{
        Optional<Location> l = this.getLocationById(locationId);
        if(l.isPresent()) {
            Optional<Stock> stock = l.get().getLocationStocks().stream().filter(s -> s.getId() == stockId).findAny();
            if(!stock.isPresent()){
                throw new StockNotFound();
            }
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
                System.out.println("-> " + productService.getProductById(s.getProductId()).get().getName() + ": " + s.getProductQuantity());
            }
        }
    }

}
