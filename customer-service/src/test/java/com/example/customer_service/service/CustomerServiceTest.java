package com.example.customer_service.service;

import com.example.customer_service.dto.CustomerRequestDTO;
import com.example.customer_service.dto.CustomerResponseDTO;
import com.example.customer_service.dto.LoginRequestDTO;
import com.example.customer_service.entity.Customer;
import com.example.customer_service.exception.InvalidCredentialsException;
import com.example.customer_service.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    private PasswordEncoder passwordEncoder;
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
        customerService = new CustomerService(customerRepository, passwordEncoder);
    }

    @Test
    void registerEncodesPassword() {
        when(customerRepository.existsByEmail("student@example.com")).thenReturn(false);
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> {
            Customer customer = invocation.getArgument(0);
            customer.setId(1L);
            return customer;
        });

        CustomerRequestDTO request = new CustomerRequestDTO(
                "Student", "student@example.com", "secret", "Ha Noi"
        );
        CustomerResponseDTO response = customerService.register(request);

        assertFalse(response.toString().contains("secret"));
    }

    @Test
    void loginAcceptsCorrectPassword() {
        Customer customer = customerWithEncodedPassword("secret");
        when(customerRepository.findByEmail(customer.getEmail())).thenReturn(Optional.of(customer));

        CustomerResponseDTO response = customerService.login(
                new LoginRequestDTO(customer.getEmail(), "secret")
        );

        assertTrue(response.email().equals(customer.getEmail()));
    }

    @Test
    void loginRejectsWrongPassword() {
        Customer customer = customerWithEncodedPassword("secret");
        when(customerRepository.findByEmail(customer.getEmail())).thenReturn(Optional.of(customer));

        assertThrows(InvalidCredentialsException.class, () -> customerService.login(
                new LoginRequestDTO(customer.getEmail(), "wrong")
        ));
    }

    private Customer customerWithEncodedPassword(String rawPassword) {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFullName("Student");
        customer.setEmail("student@example.com");
        customer.setPassword(passwordEncoder.encode(rawPassword));
        customer.setAddress("Ha Noi");
        return customer;
    }
}

