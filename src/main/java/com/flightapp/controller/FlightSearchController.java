package com.flightapp.controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.service.FlightSearchService;
import com.flightapp.dto.request.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.flightapp.dto.response.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/flight")
public class FlightSearchController {

    private final FlightSearchService flightSearchService;

    @PostMapping("/search")
    public Flux<FlightSearchResponse> search(@Valid @RequestBody Mono<FlightSearchRequest> reqMono) {
        return reqMono.flatMapMany(req ->
                flightSearchService.search(
                        req.getOrigin(),
                        req.getDestination(),
                        req.getDepartureDate()
                )
        );
    }
}
