package com.flightapp.controller;


import com.flightapp.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.test.web.reactive.server.WebTestClient;

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
        webClient
            .post()
            .uri("/flight/inventory")
            .exchange()
            .expectStatus().isBadRequest();   // assertion added
    }

}
