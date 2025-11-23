package com.flightapp.controller;

import com.flightapp.dto.request.BookingRequest;
import com.flightapp.dto.response.BookingResponse;
import com.flightapp.model.Booking;
import com.flightapp.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/{inventoryId}")
    public Mono<ResponseEntity<BookingResponse>> bookFlight(
            @PathVariable String inventoryId,
            @Valid @RequestBody Mono<BookingRequest> requestMono) {

        return requestMono
                .flatMap(req -> bookingService.bookFlight(inventoryId, req))
                .map(this::mapToResponse)
                .map(res -> ResponseEntity.status(HttpStatus.CREATED).body(res));
    }

    @GetMapping("/ticket/{pnr}")
    public Mono<BookingResponse> getBookingByPnr(@PathVariable String pnr) {
        return bookingService.findByPnr(pnr)
                .map(this::mapToResponse);
    }

    @GetMapping("/history/{userId}")
    public Flux<BookingResponse> bookingHistory(@PathVariable String userId) {
        return bookingService.getBookingHistory(userId)
                .map(this::mapToResponse);
    }

    @DeleteMapping("/cancel/{pnr}")
    public Mono<ResponseEntity<Void>> cancelBooking(@PathVariable String pnr) {
        return bookingService.cancelBooking(pnr)
                .thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    private BookingResponse mapToResponse(Booking booking) {
        return BookingResponse.builder()
                .pnr(booking.getPnr())
                .status(booking.getStatus())
                .totalAmount(booking.getTotalAmount())
                .bookingTime(booking.getBookingTime())
                .inventoryId(booking.getInventoryId())
                .passengers(booking.getPassengers())
                .build();
    }
}
