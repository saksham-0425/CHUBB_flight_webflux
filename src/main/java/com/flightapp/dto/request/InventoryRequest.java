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

   
    private String flightId;

    @NotNull(message = "Departure time is required")
    private Instant departureTime;

    @NotNull(message = "Arrival time is required")
    private Instant arrivalTime;

    private int availableSeats;

    private Map<String, Inventory.FareBucket> fareBuckets;

    private Map<String, String> seatMap;
}
