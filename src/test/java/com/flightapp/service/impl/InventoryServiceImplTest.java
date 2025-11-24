package com.flightapp.service.impl;

import com.flightapp.exception.ResourceNotFoundException;
import com.flightapp.model.Inventory;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {

    @Mock
    InventoryRepository inventoryRepository;

    @InjectMocks
    InventoryServiceImpl inventoryService;

    private Inventory sample;

    @BeforeEach
    void setUp() {
        Map<String, Inventory.FareBucket> fares = new HashMap<>();
        fares.put("economy", Inventory.FareBucket.builder().price(3500).available(5).build());

        sample = Inventory.builder()
                .id("inv-1")
                .flightId("flight-1")
                .departureTime(Instant.parse("2025-12-01T10:00:00Z"))
                .arrivalTime(Instant.parse("2025-12-01T12:00:00Z"))
                .availableSeats(5)
                .fareBuckets(fares)
                .seatMap(new HashMap<>())
                .build();
    }

    @Test
    void addInventory_shouldSave() {
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(Mono.just(sample));

        StepVerifier.create(inventoryService.addInventory(sample))
                .expectNextMatches(inv -> "inv-1".equals(inv.getId()) && inv.getFlightId().equals("flight-1"))
                .verifyComplete();

        verify(inventoryRepository, times(1)).save(any(Inventory.class));
    }

    @Test
    void getInventoryById_shouldReturnInventory() {
        when(inventoryRepository.findById("inv-1")).thenReturn(Mono.just(sample));

        StepVerifier.create(inventoryService.getInventoryById("inv-1"))
                .expectNextMatches(inv -> inv.getId().equals("inv-1"))
                .verifyComplete();
    }

    @Test
    void getInventoryById_shouldThrowWhenNotFound() {
        when(inventoryRepository.findById("missing")).thenReturn(Mono.empty());

        StepVerifier.create(inventoryService.getInventoryById("missing"))
                .expectError(ResourceNotFoundException.class)
                .verify();
    }

    @Test
    void searchFlights_shouldReturnFlux() {
        when(inventoryRepository.searchByRouteAndDate(eq("DEL"), eq("BLR"), any(Instant.class)))
                .thenReturn(Flux.just(sample));

        StepVerifier.create(inventoryService.searchFlights("DEL", "BLR", Instant.parse("2025-12-01T00:00:00Z")))
                .expectNextCount(1)
                .verifyComplete();

        verify(inventoryRepository, times(1)).searchByRouteAndDate(eq("DEL"), eq("BLR"), any(Instant.class));
    }
}
