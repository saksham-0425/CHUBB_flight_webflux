package com.flightapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightSearchRequest {

    @NotBlank(message = "Origin airport code is required")
    private String origin;

    @NotBlank(message = "Destination airport code is required")
    private String destination;

    @NotNull(message = "Departure date must be provided")
    private Instant departureDate;
}
