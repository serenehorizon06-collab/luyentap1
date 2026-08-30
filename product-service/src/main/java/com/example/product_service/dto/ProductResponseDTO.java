package com.example.product_service.dto;

import java.math.BigDecimal;

public record ProductResponseDTO(
        Long id,
        String name,
        BigDecimal price,
        Integer stockQuantity,
        String description
) {
}

