package com.flightapp.service.impl;

import com.flightapp.dto.request.BookingRequest;
import com.flightapp.exception.BookingException;
import com.flightapp.exception.InvalidRequestException;
import com.flightapp.exception.ResourceNotFoundException;
import com.flightapp.exception.SeatUnavailableException;
import com.flightapp.model.Booking;
import com.flightapp.model.Inventory;
import com.flightapp.model.Passenger;
import com.flightapp.repository.BookingRepository;
import com.flightapp.repository.InventoryRepository;
import com.flightapp.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final InventoryRepository inventoryRepository;
    private final BookingRepository bookingRepository;

    private String generatePnr() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @Override
    public Mono<Booking> bookFlight(final String inventoryId, final BookingRequest request) {
        if (request == null || request.getPassengers() == null || request.getPassengers().isEmpty()) {
            return Mono.error(new InvalidRequestException("Invalid booking request"));
        }

        return inventoryRepository.findById(inventoryId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Inventory not found: " + inventoryId)))
                .flatMap(inventory -> validateSeats(inventory, request))
                .flatMap(inventory -> createBooking(request, inventory));
    }

    private Mono<Inventory> validateSeats(Inventory inventory, BookingRequest request) {
        Map<String, String> seatMap = inventory.getSeatMap();

        List<String> requestedSeats = request.getPassengers()
                .stream()
                .map(Passenger::getSeatNumber)
                .collect(Collectors.toList());

        for (String seat : requestedSeats) {
            if (!seatMap.containsKey(seat) || !"AVAILABLE".equals(seatMap.get(seat))) {
                return Mono.error(new SeatUnavailableException("Seat not available: " + seat));
            }
        }

        requestedSeats.forEach(seat -> seatMap.put(seat, "BOOKED"));
        return inventoryRepository.save(inventory);
    }

    private Mono<Booking> createBooking(BookingRequest request, Inventory inventory) {
        Booking booking = Booking.builder()
                .inventoryId(inventory.getId())
                .userId(request.getUserId())
                .pnr(generatePnr())
                .status("CONFIRMED")
                .bookingTime(Instant.now())
                .totalAmount(request.getTotalAmount())
                .passengers(request.getPassengers())
                .build();

        return bookingRepository.save(booking);
    }

    @Override
    public Mono<Booking> findByPnr(final String pnr) {
        return bookingRepository.findByPnr(pnr)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("PNR not found: " + pnr)));
    }

    @Override
    public Flux<Booking> getBookingHistory(final String userId) {
        return bookingRepository.findByUserId(userId);
    }

    @Override
    public Mono<Void> cancelBooking(final String pnr) {
        return bookingRepository.findByPnr(pnr)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Booking not found for PNR: " + pnr)))
                .flatMap(booking -> inventoryRepository.findById(booking.getInventoryId())
                        .flatMap(inventory -> restoreSeats(inventory, booking)
                                .then(bookingRepository.delete(booking))))
                .then();
    }

    private Mono<Inventory> restoreSeats(Inventory inventory, Booking booking) {
        Map<String, String> seatMap = inventory.getSeatMap();

        booking.getPassengers()
                .forEach(p -> seatMap.put(p.getSeatNumber(), "AVAILABLE"));

        return inventoryRepository.save(inventory);
    }
}
