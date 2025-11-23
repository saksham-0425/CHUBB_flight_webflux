package com.flightapp.exception;
public class BookingException extends ApplicationException {

    private static final long serialVersionUID = 1L;

    public BookingException(final String message) {
        super("BOOKING_ERROR", message);
    }
}
