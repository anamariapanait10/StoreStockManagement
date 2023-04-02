package com.store_inventory.model;
import com.store_inventory.model.abstracts.AbstractEntity;
import com.store_inventory.model.enums.ProductType;
import lombok.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Product extends AbstractEntity {
    private String name;
    private UUID categoryId;
    private LocalDate expirationDate;
    private float price;
    private ProductType productType;
    private HashMap<String, String> properties;
}
