package com.flightapp.controller;

import com.flightapp.dto.request.BookingRequest;

import com.flightapp.model.Booking;
import com.flightapp.model.Passenger;
import com.flightapp.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class BookingControllerTest {

    @Mock
    BookingService bookingService;

    WebTestClient webClient;

    @BeforeEach
    void setUp() {
        openMocks(this);
        BookingController controller = new BookingController(bookingService);
        webClient = WebTestClient.bindToController(controller).build();
    }

    @Test
    void bookFlight_shouldReturnCreated() {
    	BookingRequest req = BookingRequest.builder()
    		    .userId("u1")
    		    .passengers(Collections.singletonList(
    		        Passenger.builder()
    		            .name("John")
    		            .gender("M")
    		            .age(30)
    		            .meal("VEG")
    		            .seatNumber("A1")
    		            .build()
    		    ))
    		    .totalAmount(3500)
    		    .build();

        Booking booking = Booking.builder()
                .pnr("PNR123")
                .inventoryId("inv-1")
                .userId("u1")
                .status("CONFIRMED")
                .bookingTime(Instant.now())
                .totalAmount(3500)
                .passengers(req.getPassengers())
                .build();

        when(bookingService.bookFlight(eq("inv-1"), any(BookingRequest.class))).thenReturn(Mono.just(booking));

        webClient.post()
                .uri("/booking/inv-1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.pnr").isEqualTo("PNR123")
                .jsonPath("$.inventoryId").isEqualTo("inv-1");

        verify(bookingService, times(1)).bookFlight(eq("inv-1"), any(BookingRequest.class));
    }

    @Test
    void getBookingByPnr_shouldReturnBooking() {
        Booking booking = Booking.builder().pnr("PNR123").inventoryId("inv-1").userId("u1").build();
        when(bookingService.findByPnr("PNR123")).thenReturn(Mono.just(booking));

        webClient.get()
                .uri("/booking/ticket/PNR123")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.pnr").isEqualTo("PNR123");

        verify(bookingService, times(1)).findByPnr("PNR123");
    }

    @Test
    void cancelBooking_shouldReturnNoContent() {
        when(bookingService.cancelBooking("PNR123")).thenReturn(Mono.empty());

        webClient.delete()
                .uri("/booking/cancel/PNR123")
                .exchange()
                .expectStatus().isNoContent();

        verify(bookingService, times(1)).cancelBooking("PNR123");
    }
}
