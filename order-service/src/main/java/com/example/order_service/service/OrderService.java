package com.example.order_service.service;

import com.example.order_service.dto.OrderRequestDTO;
import com.example.order_service.dto.OrderResponseDTO;
import com.example.order_service.dto.ProductResponseDTO;
import com.example.order_service.entity.Order;
import com.example.order_service.exception.OrderNotFoundException;
import com.example.order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    public OrderService(OrderRepository orderRepository, ProductClient productClient) {
        this.orderRepository = orderRepository;
        this.productClient = productClient;
    }

    public OrderResponseDTO create(OrderRequestDTO request) {
        ProductResponseDTO product = productClient.getProduct(request.productId());
        BigDecimal totalAmount = product.price().multiply(BigDecimal.valueOf(request.quantity()));

        Order order = new Order();
        order.setCustomerId(request.customerId());
        order.setProductId(request.productId());
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(totalAmount);
        order.setStatus("CREATED");

        return toResponse(orderRepository.save(order));
    }

    public OrderResponseDTO findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return toResponse(order);
    }

    private OrderResponseDTO toResponse(Order order) {
        return new OrderResponseDTO(
                order.getId(),
                order.getCustomerId(),
                order.getProductId(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getStatus()
        );
    }
}

