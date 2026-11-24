package com.example.grocery.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.grocery.entity.Order;

public interface OrderRepository
                extends JpaRepository<Order, Long> {

        @Override
        @EntityGraph(attributePaths = {
                        "customer",
                        "orderItems",
                        "orderItems.groceryItem"
        })
        Optional<Order> findById(Long id);

        @Override
        @EntityGraph(attributePaths = {
                        "customer",
                        "orderItems",
                        "orderItems.groceryItem"
        })
        List<Order> findAll();
}
