package net.pao.laboratorpao.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccountType {
    PERSONAL(0, "Personal"),
    BUSINESS(1, "Business");

    private final Integer index;
    private final String name;
}
