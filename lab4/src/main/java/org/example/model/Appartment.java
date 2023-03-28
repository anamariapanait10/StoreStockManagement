package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.example.application.enums.RoomConnectionType;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Appartment extends Accomodation{

    private RoomConnectionType roomConnectionType;
    public Appartment(RoomConnectionType type) {
        this.roomConnectionType = type;
    }
}
