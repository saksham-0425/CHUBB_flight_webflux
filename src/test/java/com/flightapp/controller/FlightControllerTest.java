package com.flightapp.controller;

import com.flightapp.dto.request.FlightSearchRequest;
import com.flightapp.dto.response.FlightSearchResponse;
import com.flightapp.model.Inventory;
import com.flightapp.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.HashMap;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class FlightControllerTest {

    @Mock
    InventoryService inventoryService;

    WebTestClient webClient;

    @BeforeEach
    void setUp() {
        openMocks(this);
        FlightController controller = new FlightController(inventoryService);
        webClient = WebTestClient.bindToController(controller).build();
    }

   

    @Test
    void addInventory_endpointDelegatesToService() {
        // tested in InventoryControllerTest; no duplication necessary here
    }
}
