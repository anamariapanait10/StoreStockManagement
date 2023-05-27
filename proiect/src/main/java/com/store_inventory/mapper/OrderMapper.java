package com.store_inventory.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store_inventory.model.Location;
import com.store_inventory.model.Order;
import com.store_inventory.model.Product;
import com.store_inventory.model.Stock;
import com.store_inventory.model.enums.LocationType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class OrderMapper {
    private static final OrderMapper INSTANCE = new OrderMapper();

    OrderMapper(){
    }
    public static OrderMapper getInstance(){
        return INSTANCE;
    }
    public Optional<Order> mapToOrder(ResultSet resultSet) throws SQLException, JsonProcessingException {
        if (resultSet.next()) {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonOrdersMap = resultSet.getString("orders");
            Map<Product, Integer> ordersMap = objectMapper.readValue(jsonOrdersMap, Map.class);
            Order obj = Order.builder()
                    .orderLocationId(UUID.fromString(resultSet.getString("order_location_id")))
                    .supplierId(UUID.fromString(resultSet.getString("supplier_id")))
                    .transactionId(UUID.fromString(resultSet.getString("transaction_id")))
                    .orderedProducts(ordersMap)
                    .totalPrice(resultSet.getFloat("total_price"))
                    .build();
            obj.setId(UUID.fromString(resultSet.getString("id")));
            return Optional.of(obj);
        } else {
            return Optional.empty();
        }
    }

    public List<Order> mapToOrderList(ResultSet resultSet) throws SQLException, JsonProcessingException {
        List<Order> OrderList = new ArrayList<>();

        while(resultSet.next()){
            OrderList.add(mapToOneOrder(resultSet).get());
        }

        return OrderList;
    }

    private Optional<Order> mapToOneOrder(ResultSet resultSet) throws SQLException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonOrdersMap = resultSet.getString("orders");
        Map<String, Integer> jsonMap = objectMapper.readValue(jsonOrdersMap, Map.class);;
        Map<Product, Integer> ordersMap = new HashMap<>();
        for (String p : jsonMap.keySet()) {
            ordersMap.put(objectMapper.readValue(p, Product.class), jsonMap.get(p));
        }
        UUID trId = null;
        if (resultSet.getString("transaction_id") != null) {
            trId = UUID.fromString(resultSet.getString("transaction_id"));
        }
        Order obj = Order.builder()
                .orderLocationId(UUID.fromString(resultSet.getString("order_location_id")))
                .supplierId(UUID.fromString(resultSet.getString("supplier_id")))
                .transactionId(trId)
                .orderedProducts(ordersMap)
                .totalPrice(resultSet.getFloat("total_price"))
                .build();
        obj.setId(UUID.fromString(resultSet.getString("id")));
        return Optional.of(obj);

    }
}