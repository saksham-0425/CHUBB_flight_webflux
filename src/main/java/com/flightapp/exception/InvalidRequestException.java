package com.flightapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
public class InvalidRequestException extends ApplicationException {

    private static final long serialVersionUID = 1L;

    public InvalidRequestException(final String message) {
        super("INVALID_REQUEST", message);
    }

    public ResponseStatusException toResponseStatus() {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, getMessage());
    }
}
