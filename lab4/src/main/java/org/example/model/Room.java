package org.example.model;

import lombok.AllArgsConstructor;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Room {
    private UUID id;
    private List<String> features;
}
