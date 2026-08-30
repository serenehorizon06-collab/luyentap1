package com.example.customer_service.dto;

public record CustomerResponseDTO(
        Long id,
        String fullName,
        String email,
        String address
) {
}

