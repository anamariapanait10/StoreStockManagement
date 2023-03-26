package com.store_inventory.model.enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;
@Getter
@AllArgsConstructor
public enum ProductType {
    PERISHABLE("Perishable"),
    NON_PERISHABLE("Non perishable"),
    NONE("none");

    private final String typeString;

    public static ProductType getEnumByFieldString(String field){
        return Arrays.stream(ProductType.values())
                .filter(enumElement -> enumElement.typeString.equals(field))
                .findAny()
                .orElse(NONE);
    }
}
