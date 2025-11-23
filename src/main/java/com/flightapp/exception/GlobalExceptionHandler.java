package com.flightapp.exception;

import com.flightapp.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ErrorResponse buildError(String code, String message) {
        return ErrorResponse.builder()
                .code(code)
                .message(message)
                .timestamp(Instant.now().toEpochMilli())
                .build();
    }

    // 404 - NOT FOUND
    @ExceptionHandler(ResourceNotFoundException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleResourceNotFound(ResourceNotFoundException ex) {
        return Mono.just(
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(buildError(ex.getCode(), ex.getMessage()))
        );
    }

    // 400 - BAD REQUEST
    @ExceptionHandler(InvalidRequestException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleInvalidRequest(InvalidRequestException ex) {
        return Mono.just(
                ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(buildError(ex.getCode(), ex.getMessage()))
        );
    }

    // 409 - CONFLICT (seat already booked)
    @ExceptionHandler(SeatUnavailableException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleSeatUnavailable(SeatUnavailableException ex) {
        return Mono.just(
                ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(buildError(ex.getCode(), ex.getMessage()))
        );
    }

    // 422 - BUSINESS LOGIC FAILURE
    @SuppressWarnings("deprecation")
	@ExceptionHandler(BookingException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleBookingException(BookingException ex) {
        return Mono.just(
                ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                        .body(buildError(ex.getCode(), ex.getMessage()))
        );
    }

    // 500 - INTERNAL ERROR fallback
    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponse>> handleGeneralException(Exception ex) {
        return Mono.just(
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(buildError("INTERNAL_ERROR", ex.getMessage()))
        );
    }
}
