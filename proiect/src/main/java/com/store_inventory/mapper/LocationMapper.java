package com.store_inventory.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store_inventory.model.Location;
import com.store_inventory.model.Stock;
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
    public Optional<Location> mapToLocation(ResultSet resultSet) throws SQLException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        if (resultSet.next()) {
            String jsonStocksArray = resultSet.getString("stocks");
            List<Stock> stockList = objectMapper.readValue(jsonStocksArray, new TypeReference<List<Stock>>(){});
            Location obj = Location.builder()
                    .name(resultSet.getString("name"))
                    .address(resultSet.getString("address"))
                    .locationStocks(stockList)
                    .locationType(LocationType.valueOf(resultSet.getString("type")))
                    .maxStockCapacity(resultSet.getInt("max_stock_capacity"))
                    .build();
            obj.setId(UUID.fromString(resultSet.getString("id")));
            return Optional.of(obj);
        } else {
            return Optional.empty();
        }
    }

    public List<Location> mapToLocationList(ResultSet resultSet) throws SQLException, JsonProcessingException {
        List<Location> locationList = new ArrayList<>();
        while (resultSet.next()) {
            locationList.add(mapToOneLocation(resultSet).get());
        }
        return locationList;
    }

    private Optional<Location> mapToOneLocation(ResultSet resultSet) throws SQLException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStocksArray = resultSet.getString("stocks");
        List<Stock> stockList = objectMapper.readValue(jsonStocksArray, new TypeReference<List<Stock>>(){});
        Location obj = Location.builder()
                .name(resultSet.getString("name"))
                .address(resultSet.getString("address"))
                .locationStocks(stockList)
                .locationType(LocationType.valueOf(resultSet.getString("type")))
                .maxStockCapacity(resultSet.getInt("max_stock_capacity"))
                .build();
        obj.setId(UUID.fromString(resultSet.getString("id")));
        return Optional.of(obj);
    }
}