package com.example.orderservice.controller;

import com.example.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/health")
    public String health() {
        return "Order Service is UP";
    }

    @PostMapping
    public Map<String, Object> createOrder() {

        String orderId = UUID.randomUUID().toString();

        Map inventoryResponse =
                restTemplate.getForObject(
                        "http://inventory-service/api/inventory/101",
                        Map.class
                );

        orderRepository.save(orderId);

        return Map.of(
                "orderId", orderId,
                "status", "CREATED",
                "inventory", inventoryResponse
        );
    }

    @GetMapping("/{id}")
    public Map<String, String> getOrder(@PathVariable String id) {
        return Map.of(
                "orderId", id,
                "status", "CREATED"
        );
    }
}

