package com.example.customer_service.dto;

public record CustomerRequestDTO(
        String fullName,
        String email,
        String password,
        String address
) {
}

