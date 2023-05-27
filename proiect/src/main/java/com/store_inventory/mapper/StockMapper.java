package com.store_inventory.mapper;

import com.store_inventory.model.Stock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class StockMapper {
    private static final StockMapper INSTANCE = new StockMapper();

    StockMapper(){
    }
    public static StockMapper getInstance(){
        return INSTANCE;
    }
    public Optional<Stock> mapToStock(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return Optional.of(
                    Stock.builder()
                            .id(UUID.fromString(resultSet.getString("id")))
                            .product(resultSet.getString("product"))
                            .productQuantity(resultSet.getString("quantity"))
                            .build()
            );
        } else {
            return Optional.empty();
        }
    }

    public List<Stock> mapToStockList(ResultSet resultSet) throws SQLException {
        List<Stock> StockList = new ArrayList<>();

        while(resultSet.next()){
            StockList.add(mapToStock(resultSet).get());
        }

        return StockList;
    }
}
