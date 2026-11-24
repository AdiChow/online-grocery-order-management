package com.example.grocery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.grocery.entity.GroceryItem;

public interface GroceryItemRepository
        extends JpaRepository<GroceryItem, Long> {
}
