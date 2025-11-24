package com.flightapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "flights")
public class Flight {
    @Id
    private String id;

    @NotBlank
    private String flightNumber; 

    @NotBlank
    private String origin; 

    @NotBlank
    private String destination;
    private String airlineId; 
    private String aircraftType;
    private int durationMinutes;
}
