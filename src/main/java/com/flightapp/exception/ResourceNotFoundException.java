package com.flightapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
public class ResourceNotFoundException extends ApplicationException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(final String message) {
        super("RESOURCE_NOT_FOUND", message);
    }
    public ResponseStatusException toResponseStatus() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, getMessage());
    }
}
