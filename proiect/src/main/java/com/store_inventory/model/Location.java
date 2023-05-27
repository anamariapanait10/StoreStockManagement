package com.store_inventory.model;

import com.store_inventory.model.abstracts.AbstractEntity;
import com.store_inventory.model.enums.LocationType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder
public class Location extends AbstractEntity {

    private String name;
    private String address;
    private LocationType locationType;
    private int maxStockCapacity;
    private List<Stock> locationStocks = new ArrayList<>();

    public Location(String name, String address, LocationType lt, int maxStockCapacity, List<Stock> list) {
        this.name = name;
        this.address = address;
        this.locationType = lt;
        this.maxStockCapacity = maxStockCapacity;
    }
    public void addStock(Stock s) {
        locationStocks.add(s);
    }

    public void removeStock(Stock s) {
        locationStocks.remove(s);
    }

}
