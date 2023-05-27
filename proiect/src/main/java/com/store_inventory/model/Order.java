package com.store_inventory.model;

import com.store_inventory.model.abstracts.AbstractEntity;
import com.store_inventory.model.abstracts.Transaction;
import lombok.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class Order extends AbstractEntity {
    private Map<Product, Integer> orderedProducts; // UUID is product ID
    private UUID orderLocationId;
    private UUID supplierId;
    private float totalPrice;
    private UUID transactionId;

    public Order(UUID orderLocation) {
        super(UUID.randomUUID(), LocalDate.now());
        this.orderedProducts = new HashMap<>();
        this.orderLocationId = orderLocation;
        this.totalPrice = 0;
    }

    public Order(Map<Product, Integer> orderedProducts, UUID orderLocation) {
        super(UUID.randomUUID(), LocalDate.now());
        this.orderedProducts = orderedProducts;
        this.orderLocationId = orderLocation;
        updateTotalPrice();
    }

    public void updateTotalPrice() {
        this.totalPrice = 0;
        for(Product p : this.orderedProducts.keySet()) {
            this.totalPrice += p.getPrice() * this.orderedProducts.get(p);
        }
    }

}