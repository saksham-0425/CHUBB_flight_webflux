package com.flightapp.service.impl;

import com.flightapp.dto.response.FlightSearchResponse;

import com.flightapp.model.Airline;
import com.flightapp.repository.FlightRepository;
import com.flightapp.repository.InventoryRepository;
import com.flightapp.repository.AirlineRepository;
import com.flightapp.service.FlightSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


import java.time.*;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class FlightSearchServiceImpl implements FlightSearchService {

    private final FlightRepository flightRepository;
    private final InventoryRepository inventoryRepository;
    private final AirlineRepository airlineRepository;

    @Override
    public Flux<FlightSearchResponse> search(String origin, String destination, Instant departureDate) {
        Instant start = departureDate.truncatedTo(ChronoUnit.DAYS);
        Instant end = start.plus(1, ChronoUnit.DAYS);
        return flightRepository.findByOriginAndDestination(origin, destination)
                .flatMap(flight ->
                    inventoryRepository.findByFlightAndDepartureBetween(flight.getId(), start, end)
                        .flatMap(inventory ->
                            airlineRepository.findById(flight.getAirlineId())
                                .defaultIfEmpty(Airline.builder().id(flight.getAirlineId()).name("").code("").build())
                                .map(airline -> FlightSearchResponse.builder()
                                        .inventoryId(inventory.getId())
                                        .flightId(flight.getId())
                                        .airlineName(airline.getName())
                                        .airlineCode(airline.getCode())
                                        .origin(flight.getOrigin())
                                        .destination(flight.getDestination())
                                        .departureTime(inventory.getDepartureTime())
                                        .arrivalTime(inventory.getArrivalTime())
                                        .price(inventory.getFareBuckets() != null && inventory.getFareBuckets().containsKey("economy")
                                                ? inventory.getFareBuckets().get("economy").getPrice()
                                                : 0)
                                        .build()
                                )
                        )
                );
    }
}
