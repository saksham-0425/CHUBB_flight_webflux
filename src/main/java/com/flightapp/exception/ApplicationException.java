package com.flightapp.exception;
public abstract class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected final String code;

    protected ApplicationException(final String code, final String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
