package com.example.inventoryservice.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @GetMapping("/health")
    public String health() {
        return "Inventory Service is UP";
    }

    @GetMapping("/{productId}")
    public Map<String, Object> checkStock(@PathVariable String productId) {
        return Map.of(
                "productId", productId,
                "available", true,
                "quantity", 100
        );
    }
}

