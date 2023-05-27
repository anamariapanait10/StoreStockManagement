package com.store_inventory.repository;

import com.store_inventory.model.Location;
import com.store_inventory.model.Stock;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public sealed interface LocationRepository extends Repository<Location> permits LocationRepositoryImpl {
    public Optional<Location> getObjectByName (String name) throws SQLException;
    public void setLocationStocks(UUID locationId, List<Stock> stocks);
}
