package com.store_inventory.repository;

import com.store_inventory.model.Location;

import java.sql.SQLException;
import java.util.Optional;

public sealed interface LocationRepository extends Repository<Location> permits LocationRepositoryImpl {
    Optional<Location> getObjectByName (String name) throws SQLException;
}
