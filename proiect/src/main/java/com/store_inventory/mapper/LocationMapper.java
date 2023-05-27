package com.store_inventory.mapper;

import com.store_inventory.model.Location;
import com.store_inventory.model.enums.LocationType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
public class LocationMapper {
    private static final LocationMapper INSTANCE = new LocationMapper();

    private LocationMapper() {
    }

    public static LocationMapper getInstance() {
        return INSTANCE;
    }
    public Optional<Location> mapToLocation(ResultSet resultSet) throws SQLException {

        if (resultSet.next()) {
            Location obj = Location.builder()
                    .name(resultSet.getString("name"))
                    .address(resultSet.getString("address"))
                    .locationType(LocationType.valueOf(resultSet.getString("type")))
                    .maxStockCapacity(resultSet.getInt("max_stock_capacity"))
                    .build();
            obj.setId(UUID.fromString(resultSet.getString("id")));
            return Optional.of(obj);
        } else {
            return Optional.empty();
        }
    }

    public List<Location> mapToLocationList(ResultSet resultSet) throws SQLException {
        List<Location> locationList = new ArrayList<>();
        while (resultSet.next()) {
            locationList.add(mapToLocation(resultSet).get());
        }
        return locationList;
    }
}