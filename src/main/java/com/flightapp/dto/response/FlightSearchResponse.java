package com.flightapp.dto.response;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightSearchResponse {

    private String inventoryId;
    private String flightId;
    private String airlineName;
    private String airlineLogo;
    private String origin;
    private String destination;
    private Instant departureTime;
    private Instant arrivalTime;
    private long price;
}
