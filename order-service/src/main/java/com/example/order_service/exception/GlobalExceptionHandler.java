package com.example.order_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({OrderNotFoundException.class, ProductNotFoundException.class})
    public ResponseEntity<ApiResponseError> handleNotFound(RuntimeException exception) {
        return buildError(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseError> handleValidation(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(fieldError -> fieldError.getDefaultMessage())
                .orElse("Request data is invalid");
        return buildError(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(ProductServiceUnavailableException.class)
    public ResponseEntity<ApiResponseError> handleProductServiceUnavailable(
            ProductServiceUnavailableException exception
    ) {
        return buildError(HttpStatus.SERVICE_UNAVAILABLE, exception.getMessage());
    }

    private ResponseEntity<ApiResponseError> buildError(HttpStatus status, String message) {
        ApiResponseError error = new ApiResponseError(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message
        );
        return ResponseEntity.status(status).body(error);
    }
}

