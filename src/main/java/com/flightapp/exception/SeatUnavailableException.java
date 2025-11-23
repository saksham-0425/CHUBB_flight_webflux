package com.flightapp.exception;
public class SeatUnavailableException extends ApplicationException {

    private static final long serialVersionUID = 1L;

    public SeatUnavailableException(final String message) {
        super("SEAT_UNAVAILABLE", message);
    }
}
