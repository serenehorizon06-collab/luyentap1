package com.example.product_service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequestDTO(
        @NotBlank(message = "name must not be blank")
        String name,

        @NotNull(message = "price is required")
        @DecimalMin(value = "1", message = "price must be at least 1")
        BigDecimal price,

        @NotNull(message = "stockQuantity is required")
        @Min(value = 0, message = "stockQuantity must be at least 0")
        Integer stockQuantity,

        String description
) {
}

