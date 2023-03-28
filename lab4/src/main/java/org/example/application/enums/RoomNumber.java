package org.example.application.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RoomNumber {
    ONE_ROOM("One"),
    TWO_ROOMS("Two"),
    THREE_ROOMS("Three"),
    FOUR_ROOMS("Four"),
    FOUR_PLUS_ROOMS("More");

    private final String typeString;

    public static RoomNumber getEnumByFieldString(String field) {
        return Arrays.stream(RoomNumber.values())
                .filter(enumElement -> enumElement.getTypeString().equals(field))
                .findAny()
                .orElse(null);
    }

}
