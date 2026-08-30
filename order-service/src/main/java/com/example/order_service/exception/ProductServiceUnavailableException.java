package com.example.order_service.exception;

public class ProductServiceUnavailableException extends RuntimeException {

    public ProductServiceUnavailableException() {
        super("Product Service is unavailable");
    }
}

