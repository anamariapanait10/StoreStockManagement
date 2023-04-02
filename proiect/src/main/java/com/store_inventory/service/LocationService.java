package com.store_inventory.service;

import com.store_inventory.model.Category;
import com.store_inventory.model.Location;
import com.store_inventory.model.Stock;
import com.store_inventory.model.enums.LocationType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LocationService {
    List<Location> getAllLocations();
    Optional<Location> getLocationById(UUID id);
    List<Location> getLocationsByType(LocationType lt);
    void addLocation(Location l);
    void updateLocationById(UUID id, Location l);
    void removeLocationById(UUID id);
    void addStocksToLocation(UUID locationId, Stock s);
    void removeStocksFromLocation(UUID locationId, Stock s);
}
