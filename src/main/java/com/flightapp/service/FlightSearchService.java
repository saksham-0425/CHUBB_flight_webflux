package com.flightapp.service;

import com.flightapp.dto.response.FlightSearchResponse;
import reactor.core.publisher.Flux;
import java.time.Instant;

public interface FlightSearchService {
    Flux<FlightSearchResponse> search(String origin, String destination, Instant departureDate);
}
