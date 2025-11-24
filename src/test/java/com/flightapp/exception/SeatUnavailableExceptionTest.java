package com.flightapp.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SeatUnavailableExceptionTest {

    @Test
    void testSeatUnavailableMessage() {
        String msg = "Seat A1 not available";
        SeatUnavailableException ex = new SeatUnavailableException(msg);

        assertEquals(msg, ex.getMessage());
        assertTrue(ex instanceof RuntimeException);
    }
}
