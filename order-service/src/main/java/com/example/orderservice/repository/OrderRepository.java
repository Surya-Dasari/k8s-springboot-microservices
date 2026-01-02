package com.example.orderservice.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void init() {
        jdbcTemplate.execute(
            "CREATE TABLE IF NOT EXISTS orders (" +
            "id VARCHAR(64) PRIMARY KEY)"
        );
    }

    public void save(String id) {
        jdbcTemplate.update(
            "INSERT INTO orders (id) VALUES (?)",
            id
        );
    }
}

