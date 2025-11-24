package com.flightapp.service.impl;


import com.flightapp.model.Airline;
import com.flightapp.model.Flight;
import com.flightapp.model.Inventory;
import com.flightapp.repository.AirlineRepository;
import com.flightapp.repository.FlightRepository;
import com.flightapp.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightSearchServiceImplTest {

    @Mock
    FlightRepository flightRepository;
    @Mock
    InventoryRepository inventoryRepository;
    @Mock
    AirlineRepository airlineRepository;

    @InjectMocks
    FlightSearchServiceImpl service;

    private Flight flight;
    private Inventory inventory;
    private Airline airline;

    @BeforeEach
    void setUp() {
        flight = Flight.builder()
                .id("f-1")
                .flightNumber("6E101")
                .origin("DEL")
                .destination("BLR")
                .airlineId("a-1")
                .build();

        Map<String, Inventory.FareBucket> fares = new HashMap<>();
        fares.put("economy", Inventory.FareBucket.builder().price(3500).available(5).build());

        inventory = Inventory.builder()
                .id("inv-1")
                .flightId("f-1")
                .departureTime(Instant.parse("2025-12-01T10:00:00Z"))
                .arrivalTime(Instant.parse("2025-12-01T12:00:00Z"))
                .fareBuckets(fares)
                .build();

        airline = Airline.builder().id("a-1").name("Indigo").code("6E").build();
    }

    @Test
    void search_shouldReturnResults() {

        when(flightRepository.findByOriginAndDestination("DEL", "BLR"))
                .thenReturn(Flux.just(flight));

        
      
        when(inventoryRepository.findByFlightAndDepartureBetween(eq("f-1"), any(Instant.class), any(Instant.class)))
                .thenReturn(Flux.just(inventory));

        when(airlineRepository.findById("a-1")).thenReturn(Mono.just(airline));

        StepVerifier.create(service.search("DEL", "BLR", Instant.parse("2025-12-01T00:00:00Z")))
                .expectNextMatches(resp -> resp.getFlightId().equals("f-1") &&
                        "Indigo".equals(resp.getAirlineName()) &&
                        resp.getPrice() == 3500)
                .verifyComplete();

        verify(flightRepository, times(1)).findByOriginAndDestination("DEL", "BLR");
        verify(inventoryRepository, times(1)).findByFlightAndDepartureBetween(eq("f-1"), any(Instant.class), any(Instant.class));
        verify(airlineRepository, times(1)).findById("a-1");
    }

    @Test
    void search_shouldReturnEmptyWhenNoFlights() {
        when(flightRepository.findByOriginAndDestination("X", "Y")).thenReturn(Flux.empty());

        StepVerifier.create(service.search("X", "Y", Instant.now()))
                .expectNextCount(0)
                .verifyComplete();
    }
}
