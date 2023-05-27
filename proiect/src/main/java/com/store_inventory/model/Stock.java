package com.store_inventory.model;

import com.store_inventory.model.abstracts.AbstractEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
public class Stock extends AbstractEntity {
     private UUID productId;
     private int productQuantity;

}