package com.flightapp.controller;

import com.flightapp.dto.request.FlightSearchRequest;
import com.flightapp.dto.request.InventoryRequest;
import com.flightapp.dto.response.FlightSearchResponse;
import com.flightapp.model.Inventory;
import com.flightapp.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
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

    @PostMapping("/search")
    public Flux<FlightSearchResponse> searchFlights(
            @Valid @RequestBody Mono<FlightSearchRequest> requestMono) {

        return requestMono.flatMapMany(req ->
                inventoryService
                        .searchFlights(req.getOrigin(), req.getDestination(), req.getDepartureDate())
                        .flatMap(this::mapInventoryToResponse)
        );
    }
    private Inventory mapToInventory(InventoryRequest req) {
        return Inventory.builder()
                .flightId(req.getFlightId())
                .origin(req.getOrigin())
                .destination(req.getDestination())
                .departureTime(req.getDepartureTime())
                .arrivalTime(req.getArrivalTime())
                .availableSeats(req.getAvailableSeats())
                .fareBuckets(req.getFareBuckets())
                .seatMap(req.getSeatMap())
                .build();
    }
    private Mono<FlightSearchResponse> mapInventoryToResponse(Inventory inventory) {
        return Mono.just(
                FlightSearchResponse.builder()
                        .inventoryId(inventory.getId())
                        .flightId(inventory.getFlightId())
                        .origin(inventory.getOrigin())
                        .destination(inventory.getDestination())
                        .departureTime(inventory.getDepartureTime())
                        .arrivalTime(inventory.getArrivalTime())
                        .price(inventory.getFareBuckets() != null &&
                               inventory.getFareBuckets().containsKey("economy")
                               ? inventory.getFareBuckets().get("economy").getPrice()
                               : 0)
                        .build()
        );
    }
}
