package com.store_inventory.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum LocationType {

    DEPOSIT("Deposit"),
    SHOP("Shop"),
    NONE("none");

    private final String typeString;

    public static LocationType getEnumByFieldString(String field){
        return Arrays.stream(LocationType.values())
                .filter(enumElement -> enumElement.typeString.equals(field))
                .findAny()
                .orElse(NONE);
    }

}
