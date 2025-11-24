package com.flightapp.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class PassengerTest {

    @Test
    void passenger_builder_shouldSetValues() {
        Passenger p = Passenger.builder()
                .name("Alice")
                .gender("F")
                .age(28)
                .meal("VEG")
                .seatNumber("A1")
                .build();

        assertThat(p.getName()).isEqualTo("Alice");
        assertThat(p.getSeatNumber()).isEqualTo("A1");
    }

    @Test
    void passenger_setters_shouldModifyFields() {
        Passenger p = new Passenger();
        p.setName("Bob");

        assertThat(p.getName()).isEqualTo("Bob");
    }
}
