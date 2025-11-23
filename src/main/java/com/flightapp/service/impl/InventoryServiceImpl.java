package com.flightapp.service.impl;

import com.flightapp.exception.ResourceNotFoundException;
import com.flightapp.model.Inventory;
import com.flightapp.repository.InventoryRepository;
import com.flightapp.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    public Mono<Inventory> addInventory(final Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @Override
    public Mono<Inventory> getInventoryById(final String inventoryId) {
        return inventoryRepository.findById(inventoryId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Inventory not found: " + inventoryId)));
    }

    @Override
    public Flux<Inventory> searchFlights(final String origin, final String destination, final Instant departureDate) {
        return inventoryRepository
                .searchByRouteAndDate(origin, destination, departureDate)
                .switchIfEmpty(Flux.empty());
    }
}
