package org.example.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.application.enums.RoomNumber;

import java.time.LocalDate;
import java.util.HashMap;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class Accomodation {
    private int number, surface;
    private float price;
    private RoomNumber roomNumber;
    private LocalDate rentOrPurchaseDate;
    private HashMap<String, String> utilities;
    private boolean sold;
}
