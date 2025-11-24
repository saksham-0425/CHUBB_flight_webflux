package com.flightapp.controller;

import com.flightapp.model.Airline;
import com.flightapp.repository.AirlineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class AirlineControllerTest {

    @Mock
    private AirlineRepository airlineRepository;

    private WebTestClient webClient;

    @BeforeEach
    void setup() {
        openMocks(this);
        AirlineController controller = new AirlineController(airlineRepository);
        webClient = WebTestClient.bindToController(controller).build();
    }

    @Test
    void createAirline_shouldReturnCreated() {
        Airline airline = Airline.builder()
                .id("A1")
                .name("Indigo")
                .code("6E")
                .logoUrl("http://logo")
                .build();

        when(airlineRepository.save(any(Airline.class))).thenReturn(Mono.just(airline));

        webClient.post()
                .uri("/airline")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(airline)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo("A1")
                .jsonPath("$.name").isEqualTo("Indigo");

        verify(airlineRepository, times(1)).save(any(Airline.class));
    }
}
