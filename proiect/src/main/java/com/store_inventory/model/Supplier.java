package com.store_inventory.model;

import com.store_inventory.model.abstracts.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
public class Supplier extends AbstractEntity {
    private String supplierName;
    private String supplierAddress;
    private String contactNumber;

}
