package com.flightapp.service;

import com.flightapp.model.Inventory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

public interface InventoryService {

    Mono<Inventory> addInventory(Inventory inventory);

    Mono<Inventory> getInventoryById(String inventoryId);

    Flux<Inventory> searchFlights(String origin, String destination, Instant departureDate);
}
