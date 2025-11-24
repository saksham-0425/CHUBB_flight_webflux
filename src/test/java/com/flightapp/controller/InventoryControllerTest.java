package com.flightapp.controller;

import com.flightapp.dto.request.InventoryRequest;
import com.flightapp.model.Inventory;
import com.flightapp.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.HashMap;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class InventoryControllerTest {

    @Mock
    InventoryService inventoryService;

    WebTestClient webClient;

    @BeforeEach
    void setUp() {
        openMocks(this);
        InventoryController controller = new InventoryController(inventoryService);
        webClient = WebTestClient.bindToController(controller).build();
    }

    @Test
    void addInventory_shouldReturnCreated() {
        InventoryRequest req = InventoryRequest.builder()
                .departureTime(Instant.parse("2025-12-01T10:00:00Z"))
                .arrivalTime(Instant.parse("2025-12-01T12:00:00Z"))
                .availableSeats(5)
                .fareBuckets(new HashMap<>())
                .seatMap(new HashMap<>())
                .build();

        Inventory saved = Inventory.builder()
                .id("inv-123")
                .flightId("f-1")
                .departureTime(req.getDepartureTime())
                .arrivalTime(req.getArrivalTime())
                .availableSeats(req.getAvailableSeats())
                .build();

        when(inventoryService.addInventory(any(Inventory.class))).thenReturn(Mono.just(saved));

        webClient.post()
                .uri("/flight/f-1/inventory")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo("inv-123")
                .jsonPath("$.flightId").isEqualTo("f-1");

        verify(inventoryService, times(1)).addInventory(any(Inventory.class));
    }
}
