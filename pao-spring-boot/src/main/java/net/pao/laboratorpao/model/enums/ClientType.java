package net.pao.laboratorpao.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ClientType {
    INDIVIDUAL(0, "Individual"),
    LEGAL(1, "Legal");

    private final Integer index;

    private final String name;
}