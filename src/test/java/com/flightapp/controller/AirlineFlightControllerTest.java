package com.flightapp.controller;

import com.flightapp.model.Flight;
import com.flightapp.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class AirlineFlightControllerTest {

    @Mock
    private FlightRepository flightRepository;

    private WebTestClient webClient;

    @BeforeEach
    void setup() {
        openMocks(this);
        AirlineFlightController controller = new AirlineFlightController(flightRepository);
        webClient = WebTestClient.bindToController(controller).build();
    }

    @Test
    void createFlight_shouldReturnCreated() {
        Flight req = Flight.builder()
                .flightNumber("AI202")
                .origin("DEL")
                .destination("BLR")
                .aircraftType("Airbus A320")
                .durationMinutes(150)
                .build();

        Flight saved = Flight.builder()
                .id("F1")
                .flightNumber("AI202")
                .origin("DEL")
                .destination("BLR")
                .airlineId("A1")
                .aircraftType("Airbus A320")
                .durationMinutes(150)
                .build();

        when(flightRepository.save(any(Flight.class))).thenReturn(Mono.just(saved));

        webClient.post()
                .uri("/airline/A1/flight")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo("F1")
                .jsonPath("$.airlineId").isEqualTo("A1");

        verify(flightRepository).save(any(Flight.class));
    }
}
