package com.example.order_service.exception;

import java.time.LocalDateTime;

public record ApiResponseError(
        LocalDateTime timestamp,
        int status,
        String error,
        String message
) {
}

