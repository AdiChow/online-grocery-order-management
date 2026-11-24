package com.example.grocery.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.grocery.entity.Order;
import com.example.grocery.entity.OrderItem;
import com.example.grocery.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController 
@RequiredArgsConstructor 
@RequestMapping ("/orders")
public class OrderController {
private final OrderService orderService;

@PostMapping
public ResponseEntity<Order> createOrder(@RequestParam Long customerId, @RequestBody List<OrderItem> orderItems) {
    return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(customerId, orderItems));


}

@GetMapping
public List<Order> getAllOrders() {
    return orderService.getAllOrders();
}


@GetMapping("/{id}")
public Order getOrderById(@PathVariable Long id) {
    return orderService.getOrderById(id);
}

@PutMapping("/{id}")
public Order updateOrder(@PathVariable Long id,@RequestParam Long customerId, @RequestBody List<OrderItem> orderItems) {
    return orderService.updateOrder(id, customerId, orderItems);
}


@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
    orderService.deleteOrder(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();    
}


}
