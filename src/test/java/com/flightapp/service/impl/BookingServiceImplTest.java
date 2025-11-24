package com.flightapp.service.impl;

import com.flightapp.dto.request.BookingRequest;
import com.flightapp.exception.SeatUnavailableException;
import com.flightapp.model.Booking;
import com.flightapp.model.Inventory;
import com.flightapp.model.Passenger;
import com.flightapp.repository.BookingRepository;
import com.flightapp.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock
    InventoryRepository inventoryRepository;

    @Mock
    BookingRepository bookingRepository;

    @InjectMocks
    BookingServiceImpl bookingService;

    private Inventory inventory;

    @BeforeEach
    void setUp() {
        Map<String, String> seatMap = new HashMap<>();
        seatMap.put("A1", "AVAILABLE");
        seatMap.put("A2", "AVAILABLE");

        inventory = Inventory.builder()
                .id("inv-1")
                .flightId("f-1")
                .departureTime(Instant.now())
                .arrivalTime(Instant.now().plusSeconds(3600))
                .seatMap(seatMap)
                .availableSeats(2)
                .build();
    }

    @Test
    void bookFlight_shouldCreateBooking() {
        Passenger p = Passenger.builder().name("John").age(30).gender("M").seatNumber("A1").build();
        BookingRequest req = BookingRequest.builder()
                .userId("u1")
                .passengers(Collections.singletonList(p))
                .totalAmount(3500)
                .build();

        // find inventory returns inventory
        when(inventoryRepository.findById("inv-1")).thenReturn(Mono.just(inventory));
        // save updated inventory
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(Mono.just(inventory));
        // save booking
        when(bookingRepository.save(any(Booking.class))).thenAnswer(inv -> Mono.just((Booking) inv.getArgument(0)));

        StepVerifier.create(bookingService.bookFlight("inv-1", req))
                .assertNext(b -> {
                    assert b.getInventoryId().equals("inv-1");
                    assert b.getUserId().equals("u1");
                    assert b.getStatus().equals("CONFIRMED");
                    assert b.getPassengers().size() == 1;
                })
                .verifyComplete();

        verify(inventoryRepository, times(1)).findById("inv-1");
        verify(inventoryRepository, times(1)).save(any(Inventory.class));
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void bookFlight_shouldFailWhenSeatUnavailable() {
        // seat is not available (A3 doesn't exist)
        Passenger p = Passenger.builder().name("John").age(30).gender("M").seatNumber("A3").build();
        BookingRequest req = BookingRequest.builder()
                .userId("u1")
                .passengers(Collections.singletonList(p))
                .totalAmount(3500)
                .build();

        when(inventoryRepository.findById("inv-1")).thenReturn(Mono.just(inventory));

        StepVerifier.create(bookingService.bookFlight("inv-1", req))
                .expectError(SeatUnavailableException.class)
                .verify();

        verify(bookingRepository, never()).save(any());
    }

    @Test
    void cancelBooking_shouldRestoreSeatsAndDeleteBooking() {
        // create booking with passenger A1
        Passenger p = Passenger.builder().name("John").age(30).gender("M").seatNumber("A1").build();
        Booking booking = Booking.builder()
                .id("b-1")
                .inventoryId("inv-1")
                .userId("u1")
                .pnr("PNR123")
                .status("CONFIRMED")
                .bookingTime(Instant.now())
                .totalAmount(3500)
                .passengers(Collections.singletonList(p))
                .build();

        when(bookingRepository.findByPnr("PNR123")).thenReturn(Mono.just(booking));
        when(inventoryRepository.findById("inv-1")).thenReturn(Mono.just(inventory));
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(Mono.just(inventory));
        when(bookingRepository.delete(any(Booking.class))).thenReturn(Mono.empty());

        StepVerifier.create(bookingService.cancelBooking("PNR123"))
                .verifyComplete();

        verify(inventoryRepository, times(1)).save(any(Inventory.class));
        verify(bookingRepository, times(1)).delete(any(Booking.class));
    }
}
