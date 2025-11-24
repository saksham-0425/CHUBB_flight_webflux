package com.flightapp.controller;

import com.flightapp.dto.request.FlightSearchRequest;
import com.flightapp.dto.response.FlightSearchResponse;
import com.flightapp.service.FlightSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;

import java.time.Instant;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.MockitoAnnotations.openMocks;

class FlightSearchControllerTest {

    @Mock
    private FlightSearchService flightSearchService;

    private WebTestClient webClient;

    @BeforeEach
    void setUp() {
        openMocks(this);

        FlightSearchController controller = new FlightSearchController(flightSearchService);

        webClient = WebTestClient.bindToController(controller)
                .configureClient()
                .baseUrl("/flight")
                .build();
    }

    @Test
    void searchFlights_shouldReturnEmptyListWhenNoInventory() {
        FlightSearchRequest req = FlightSearchRequest.builder()
                .origin("DEL")
                .destination("BLR")
                .departureDate(Instant.parse("2025-12-01T00:00:00Z"))
                .build();

        when(flightSearchService.search(eq("DEL"), eq("BLR"), any(Instant.class)))
                .thenReturn(Flux.empty());

        webClient.post()
                .uri("/search")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(FlightSearchResponse.class)
                .hasSize(0);

        verify(flightSearchService, times(1))
                .search(eq("DEL"), eq("BLR"), any(Instant.class));
    }

    @Test
    void searchFlights_shouldReturnResults() {

        FlightSearchResponse response = FlightSearchResponse.builder()
                .flightId("FL1")
                .inventoryId("INV1")
                .origin("DEL")
                .destination("BLR")
                .departureTime(Instant.now())
                .arrivalTime(Instant.now().plusSeconds(7200))
                .price(5000)
                .build();

        FlightSearchRequest req = FlightSearchRequest.builder()
                .origin("DEL")
                .destination("BLR")
                .departureDate(Instant.parse("2025-12-01T00:00:00Z"))
                .build();

        when(flightSearchService.search(eq("DEL"), eq("BLR"), any(Instant.class)))
                .thenReturn(Flux.just(response));

        webClient.post()
                .uri("/search")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(FlightSearchResponse.class)
                .hasSize(1)
                .value(list -> {
                    FlightSearchResponse r = list.get(0);
                    assert r.getInventoryId().equals("INV1");
                });

        verify(flightSearchService, times(1))
                .search(eq("DEL"), eq("BLR"), any(Instant.class));
    }
}
