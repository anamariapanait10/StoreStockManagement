package com.store_inventory.model;

import com.store_inventory.model.abstracts.AbstractEntity;
import com.store_inventory.model.enums.LocationType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Location extends AbstractEntity {
    private String address;
    private LocationType locationType;
    private int maxStockCapacity;
    private List<Stock> locationStocks;

    public void addStock(Stock s) {
        locationStocks.add(s);
    }

    public void removeStock(Stock s) {
        locationStocks.remove(s);
    }

}
