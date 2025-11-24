package com.flightapp.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidRequestExceptionTest {

    @Test
    void testInvalidRequestMessage() {
        String msg = "Invalid booking request";
        InvalidRequestException ex = new InvalidRequestException(msg);

        assertEquals(msg, ex.getMessage());
        assertTrue(ex instanceof RuntimeException);
    }
}
