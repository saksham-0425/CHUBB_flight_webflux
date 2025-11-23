package com.flightapp.dto.request;

import com.flightapp.model.Inventory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryRequest {

    @NotBlank(message = "Flight ID is required")
    private String flightId;

    @NotBlank(message = "Origin is required")
    private String origin;

    @NotBlank(message = "Destination is required")
    private String destination;

    @NotNull(message = "Departure time is required")
    private Instant departureTime;

    @NotNull(message = "Arrival time is required")
    private Instant arrivalTime;

    private int availableSeats;

    private Map<String, Inventory.FareBucket> fareBuckets;

    private Map<String, String> seatMap;
}
