package com.flightapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import jakarta.validation.Valid;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Document(collection = "bookings")
public class Booking {

    @Id
    private String id;

    @NotBlank(message = "userId required")
    private String userId;

    @NotBlank(message = "inventoryId required")
    private String inventoryId;

    @Indexed(unique = true)
    private String pnr;

    private String status;

    private long totalAmount;

    @NotNull
    private Instant bookingTime;

    @Valid
    private List<Passenger> passengers;
}
