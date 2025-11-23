package com.flightapp.service;

import com.flightapp.model.Booking;
import com.flightapp.dto.request.BookingRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookingService {

    Mono<Booking> bookFlight(String inventoryId, BookingRequest request);

    Mono<Booking> findByPnr(String pnr);

    Flux<Booking> getBookingHistory(String userId);

    Mono<Void> cancelBooking(String pnr);
}
