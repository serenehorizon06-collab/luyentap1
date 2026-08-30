package com.example.order_service.service;

import com.example.order_service.dto.OrderRequestDTO;
import com.example.order_service.dto.OrderResponseDTO;
import com.example.order_service.dto.ProductResponseDTO;
import com.example.order_service.entity.Order;
import com.example.order_service.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductClient productClient;

    @InjectMocks
    private OrderService orderService;

    @Test
    void createCalculatesTotalAmountFromProductServicePrice() {
        ProductResponseDTO product = new ProductResponseDTO(
                2L, "Keyboard", new BigDecimal("125.50"), 20, "Mechanical keyboard"
        );
        when(productClient.getProduct(2L)).thenReturn(product);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            order.setId(1L);
            return order;
        });

        OrderResponseDTO response = orderService.create(new OrderRequestDTO(10L, 2L, 3));

        assertEquals(new BigDecimal("376.50"), response.totalAmount());
        assertEquals("CREATED", response.status());
        assertEquals(10L, response.customerId());
        assertEquals(2L, response.productId());
    }
}

