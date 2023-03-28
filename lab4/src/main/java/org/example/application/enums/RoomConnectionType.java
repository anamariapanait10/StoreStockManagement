package org.example.application.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RoomConnectionType {
    INTERCONNECTED("Connected"),
    PARTIAL_INTERCONNECTED("Partial"),
    NOT_INTERCONNECTED("Not");

    private final String typeString;

    public static RoomConnectionType getEnumByFieldString(String field) {
        return Arrays.stream(RoomConnectionType.values())
                .filter(enumElement -> enumElement.getTypeString().equals(field))
                .findAny()
                .orElse(null);
    }
}
