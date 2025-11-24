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
public class InventoryController {

    private final InventoryService inventoryService;
    @PostMapping("/{flightId}/inventory")
    public Mono<ResponseEntity<Inventory>> addInventory(
            @PathVariable String flightId,
            @Valid @RequestBody Mono<InventoryRequest> reqMono) {

        return reqMono
                .map(req -> Inventory.builder()
                        .flightId(flightId)          // set from path variable
                        .departureTime(req.getDepartureTime())
                        .arrivalTime(req.getArrivalTime())
                        .availableSeats(req.getAvailableSeats())
                        .fareBuckets(req.getFareBuckets())
                        .seatMap(req.getSeatMap())
                        .build()
                )
                .flatMap(inventoryService::addInventory)
                .map(saved -> ResponseEntity.status(HttpStatus.CREATED).body(saved));
    }


}
