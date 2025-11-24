package com.flightapp.controller;

import com.flightapp.model.Flight;
import com.flightapp.repository.FlightRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/airline/{airlineId}/flight")
public class AirlineFlightController {

    private final FlightRepository flightRepository;

    @PostMapping
    public Mono<ResponseEntity<Flight>> createFlight(
            @PathVariable String airlineId,
            @Valid @RequestBody Mono<Flight> req) {

        return req
                .map(f -> { f.setAirlineId(airlineId); return f; })
                .flatMap(flightRepository::save)
                .map(saved -> ResponseEntity.status(HttpStatus.CREATED).body(saved));
    }
}
