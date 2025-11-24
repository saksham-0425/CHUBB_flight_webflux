package com.flightapp.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class FlightTest {

    @Test
    void flight_builder_shouldWorkCorrectly() {
        Flight f = Flight.builder()
                .id("F1")
                .flightNumber("AI202")
                .origin("DEL")
                .destination("BLR")
                .airlineId("A1")
                .aircraftType("A320")
                .durationMinutes(150)
                .build();

        assertThat(f.getFlightNumber()).isEqualTo("AI202");
        assertThat(f.getOrigin()).isEqualTo("DEL");
        assertThat(f.getDestination()).isEqualTo("BLR");
        assertThat(f.getAirlineId()).isEqualTo("A1");
    }

    @Test
    void flight_setters_shouldModifyFields() {
        Flight f = new Flight();
        f.setOrigin("HYD");
        f.setDestination("MAA");

        assertThat(f.getOrigin()).isEqualTo("HYD");
        assertThat(f.getDestination()).isEqualTo("MAA");
    }
}
