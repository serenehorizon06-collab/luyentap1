package com.example.order_service.service;

import com.example.order_service.dto.ProductResponseDTO;
import com.example.order_service.exception.ProductNotFoundException;
import com.example.order_service.exception.ProductServiceUnavailableException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductClient {

    private final RestTemplate restTemplate;
    private final String productServiceUrl;

    public ProductClient(
            RestTemplate restTemplate,
            @Value("${product.service.url}") String productServiceUrl
    ) {
        this.restTemplate = restTemplate;
        this.productServiceUrl = productServiceUrl;
    }

    public ProductResponseDTO getProduct(Long productId) {
        try {
            ProductResponseDTO product = restTemplate.getForObject(
                    productServiceUrl + "/api/v1/products/{id}",
                    ProductResponseDTO.class,
                    productId
            );
            if (product == null) {
                throw new ProductServiceUnavailableException();
            }
            return product;
        } catch (HttpClientErrorException exception) {
            if (exception.getStatusCode().value() == 404) {
                throw new ProductNotFoundException(productId);
            }
            throw new ProductServiceUnavailableException();
        } catch (RestClientException exception) {
            throw new ProductServiceUnavailableException();
        }
    }
}

