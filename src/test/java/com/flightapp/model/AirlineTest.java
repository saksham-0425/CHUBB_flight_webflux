package com.flightapp.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class AirlineTest {

    @Test
    void airline_builder_shouldCreateObject() {
        Airline airline = Airline.builder()
                .id("A1")
                .name("Indigo")
                .code("6E")
                .logoUrl("url")
                .build();

        assertThat(airline.getId()).isEqualTo("A1");
        assertThat(airline.getName()).isEqualTo("Indigo");
        assertThat(airline.getCode()).isEqualTo("6E");
        assertThat(airline.getLogoUrl()).isEqualTo("url");
    }

    @Test
    void airline_setters_shouldModifyFields() {
        Airline airline = new Airline();
        airline.setId("A2");
        airline.setName("Air India");
        airline.setCode("AI");

        assertThat(airline.getId()).isEqualTo("A2");
        assertThat(airline.getName()).isEqualTo("Air India");
        assertThat(airline.getCode()).isEqualTo("AI");
    }
}
