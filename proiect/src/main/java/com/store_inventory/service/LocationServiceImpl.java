package com.store_inventory.service;

import com.store_inventory.exceptions.LocationNotFound;
import com.store_inventory.exceptions.StockNotFound;
import com.store_inventory.model.*;
import com.store_inventory.model.enums.LocationType;
import com.store_inventory.repository.LocationRepository;
import com.store_inventory.repository.LocationRepositoryImpl;


import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public final class LocationServiceImpl implements LocationService {

    private static final LocationRepository locationRepository = new LocationRepositoryImpl();
    private static ProductService productService;


    public LocationServiceImpl(ProductService productService){
        LocationServiceImpl.productService = productService;
    }
    @Override
    public List<Location> getAllLocations() {
        return locationRepository.getAll();
    }

    @Override
    public Optional<Location> getLocationById(UUID id) {
        return locationRepository.getAll().stream().filter(l -> Objects.equals(id, l.getId())).findAny();
    }

    @Override
    public Optional<Location> getLocationByName(String locationName) throws LocationNotFound {
        Optional<Location> loc = locationRepository.getAll().stream().filter(l -> Objects.equals(l.getName(), locationName)).findAny();
        if (!loc.isPresent()){
            throw new LocationNotFound();
        }
        return loc;
    }

    @Override
    public List<Location> getLocationsByType(LocationType lt) {
        return locationRepository.getAll().stream().filter(l -> Objects.equals(l.getLocationType(), lt)).collect(Collectors.toList());
    }

    @Override
    public void addLocation(Location l) {
        try {
            locationRepository.addNewObject(l);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateLocationById(UUID id, Location l) {
        this.removeLocationById(id);
        this.addLocation(l);
    }

    @Override
    public void removeLocationById(UUID id) {
        locationRepository.deleteObjectById(id);
    }

    @Override
    public void addStocksToLocation(UUID locationId, Stock s) {
        Optional<Location> l = this.getLocationById(locationId);
        if(l.isPresent()) {
            if (l.get().getLocationStocks() == null){
                l.get().setLocationStocks(new ArrayList<>());
            }
            l.get().addStock(s);

            locationRepository.setLocationStocks(locationId, l.get().getLocationStocks());
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
            Optional<Stock> stock = l.get().getLocationStocks().stream().filter(s -> Objects.equals(s.getId(), stockId)).findAny();
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
        for(Location l: locationRepository.getAll()) {
            System.out.println("Location " + l.getName() + "(" + l.getLocationType() + "):");
            if (l.getLocationStocks() != null)
                for(Stock s: l.getLocationStocks()){
                    System.out.println("-> " + productService.getProductById(s.getProductId()).get().getName() + ": " + s.getProductQuantity());
                }
        }
    }

}
