package com.flightapp.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookingExceptionTest {

    @Test
    void testBookingExceptionMessage() {
        String msg = "Booking failed";
        BookingException ex = new BookingException(msg);

        assertEquals(msg, ex.getMessage());
        assertTrue(ex instanceof RuntimeException);
    }
}
