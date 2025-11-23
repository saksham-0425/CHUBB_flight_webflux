package com.flightapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Document(collection = "flights")
public class Flight {

    @Id
    private String id;

    @NotBlank(message = "Flight number required")
    private String flightNumber;

    @NotBlank(message = "Origin required")
    private String origin;

    @NotBlank(message = "Destination required")
    private String destination;

    @Positive(message = "Duration must be positive")
    private int durationMinutes;

    @NotBlank(message = "airlineId required")
    private String airlineId;

    private String aircraftType;
}
