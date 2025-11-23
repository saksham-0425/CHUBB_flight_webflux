package com.flightapp.dto.response;

import com.flightapp.model.Passenger;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {

    private String pnr;
    private String status;
    private long totalAmount;
    private Instant bookingTime;
    private String inventoryId;
    private List<Passenger> passengers;
}
