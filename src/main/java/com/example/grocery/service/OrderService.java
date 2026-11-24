package com.example.grocery.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.grocery.entity.*;
import com.example.grocery.exception.ResourceNotFoundException;
import com.example.grocery.repository.*;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final GroceryItemRepository groceryItemRepository;

    @Transactional
    public Order createOrder(Long customerId, List<OrderItem> orderItems) {
        if (orderItems == null || orderItems.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }
        
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());

        
        BigDecimal totalPrice = orderItems.stream()
                .map(item -> {
                    if (item.getQuantity() == null || item.getQuantity() <= 0) {
                        throw new IllegalArgumentException(
                                "Quantity must be greater than zero");
                    }

                    if (item.getGroceryItem() == null || item.getGroceryItem().getId() == null) {
                        throw new IllegalArgumentException("Grocery item ID is required");
                    }

                    GroceryItem groceryItem = groceryItemRepository.findById(item.getGroceryItem().getId())
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Grocery item not found with id: " + item.getGroceryItem().getId()));
                    item.setGroceryItem(groceryItem);
                    item.setPrice(groceryItem.getPrice());
                    item.setOrder(order);

                    return groceryItem.getPrice()
                            .multiply(BigDecimal.valueOf(item.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalPrice(totalPrice);
        order.setOrderItems(orderItems);
        return orderRepository.save(order);

    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    @Transactional
    public Order updateOrder(Long orderId, Long customerId, List<OrderItem> orderItems) {

        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));

        existingOrder.setCustomer(customer);

        if (orderItems == null || orderItems.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }

        BigDecimal totalPrice = orderItems.stream()
                .map(item -> {
                    if (item.getQuantity() == null || item.getQuantity() <= 0) {
                        throw new IllegalArgumentException("Quantity must be greater than zero");
                    }

                    if (item.getGroceryItem() == null || item.getGroceryItem().getId() == null) {
                        throw new IllegalArgumentException("Grocery item ID is required");
                    }

                    GroceryItem groceryItem = groceryItemRepository.findById(item.getGroceryItem().getId())
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Grocery item not found with id: " + item.getGroceryItem().getId()));
                    item.setGroceryItem(groceryItem);
                    item.setPrice(groceryItem.getPrice());
                    item.setOrder(existingOrder);

                    return groceryItem.getPrice()
                            .multiply(BigDecimal.valueOf(item.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        existingOrder.setTotalPrice(totalPrice);
        existingOrder.getOrderItems().clear();
        existingOrder.getOrderItems().addAll(orderItems);
        return orderRepository.save(existingOrder);

    }

    @Transactional
    public void deleteOrder(Long orderId) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        orderRepository.delete(existingOrder);
    }

}
