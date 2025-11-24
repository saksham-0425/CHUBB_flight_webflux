package com.flightapp.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.time.Instant;
import java.util.Collections;

class BookingTest {

    @Test
    void booking_builder_shouldPopulateCorrectly() {
        Passenger p = Passenger.builder().name("John").seatNumber("A1").build();

        Booking b = Booking.builder()
                .id("B1")
                .userId("U1")
                .inventoryId("INV1")
                .pnr("PNR123")
                .status("CONFIRMED")
                .bookingTime(Instant.now())
                .totalAmount(3000)
                .passengers(Collections.singletonList(p))
                .build();

        assertThat(b.getUserId()).isEqualTo("U1");
        assertThat(b.getPassengers()).hasSize(1);
        assertThat(b.getPassengers().get(0).getName()).isEqualTo("John");
    }

    @Test
    void booking_setters_shouldWork() {
        Booking b = new Booking();
        b.setStatus("CANCELLED");

        assertThat(b.getStatus()).isEqualTo("CANCELLED");
    }
}
