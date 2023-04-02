package com.store_inventory.model;

import com.store_inventory.model.abstracts.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Supplier extends AbstractEntity {

    private UUID supplierId;
    private String supplierName;
    private String supplierAddress;
    private String contactNumber;

}
