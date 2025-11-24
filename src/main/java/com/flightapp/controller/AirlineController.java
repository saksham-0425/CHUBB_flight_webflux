package com.flightapp.controller;

import com.flightapp.model.Airline;
import com.flightapp.repository.AirlineRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/airline")
public class AirlineController {

    private final AirlineRepository airlineRepository;

    @PostMapping
    public Mono<ResponseEntity<Airline>> createAirline(
            @Valid @RequestBody Mono<Airline> req) {

        return req.flatMap(airlineRepository::save)
                .map(saved -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(saved));
    }
}
