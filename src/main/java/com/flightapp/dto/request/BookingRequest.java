package com.flightapp.dto.request;

import com.flightapp.model.Passenger;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequest {

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotEmpty(message = "Passengers list cannot be empty")
    @Valid
    private List<Passenger> passengers;

    @Positive(message = "Total amount must be positive")
    private long totalAmount;
}
