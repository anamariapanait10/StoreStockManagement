package com.store_inventory.model;

import com.store_inventory.model.abstracts.AbstractEntity;
import com.store_inventory.model.enums.LocationType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
public class Location extends AbstractEntity {

    private String name;
    private String address;
    private LocationType locationType;
    private int maxStockCapacity;
    private List<Stock> locationStocks;

    public Location(String name, String address, LocationType lt, int maxStockCapacity, List<Stock> list) {
        this.name = name;
        this.address = address;
        this.locationType = lt;
        this.maxStockCapacity = maxStockCapacity;
        locationStocks = new ArrayList<>();
    }
    public void addStock(Stock s) {
        locationStocks.add(s);
    }

    public void removeStock(Stock s) {
        locationStocks.remove(s);
    }

}
