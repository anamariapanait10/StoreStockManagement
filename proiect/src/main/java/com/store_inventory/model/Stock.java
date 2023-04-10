package com.store_inventory.model;

import com.store_inventory.model.abstracts.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Stock extends AbstractEntity {
     private Product product;
     private int productQuantity;

}
