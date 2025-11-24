package com.flightapp.controller;


import com.flightapp.dto.request.InventoryRequest;

import com.flightapp.model.Inventory;
import com.flightapp.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/flight")
public class FlightController {

    private final InventoryService inventoryService;
    @PostMapping("/inventory")
    public Mono<ResponseEntity<Inventory>> addInventory(
            @Valid @RequestBody Mono<InventoryRequest> requestMono) {

        return requestMono
                .map(this::mapToInventory)
                .flatMap(inventoryService::addInventory)
                .map(saved -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(saved));
    }

   
    private Inventory mapToInventory(InventoryRequest req) {
        return Inventory.builder()
                .flightId(req.getFlightId())
                
              
                .departureTime(req.getDepartureTime())
                .arrivalTime(req.getArrivalTime())
                .availableSeats(req.getAvailableSeats())
                .fareBuckets(req.getFareBuckets())
                .seatMap(req.getSeatMap())
                .build();
    }
    
}
