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
import org.springframework.web.bind.annotation.RestController;

import com.example.grocery.entity.GroceryItem;
import com.example.grocery.service.GroceryItemService;

import lombok.RequiredArgsConstructor;

@RestController 
@RequiredArgsConstructor 
@RequestMapping("/grocery-items")
public class GroceryItemController {

    
private final GroceryItemService groceryItemService;

   
@PostMapping 
public ResponseEntity<GroceryItem> createGroceryItem(@RequestBody GroceryItem groceryItem) {
    return ResponseEntity.status(HttpStatus.CREATED).body(groceryItemService.createGroceryItem(groceryItem));
}
    
@GetMapping 
public List<GroceryItem> getAllGroceryItems() {
    return groceryItemService.getAllGroceryItems();
}

@GetMapping("/{id}")
public GroceryItem getGroceryItemById(@PathVariable Long id) {
    return groceryItemService.getGroceryItemById(id);   
}

@PutMapping("/{id}")
public GroceryItem updateGroceryItem(@PathVariable Long id, @RequestBody GroceryItem groceryItem) {
    return groceryItemService.updateGroceryItem(id, groceryItem);
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteGroceryItem(@PathVariable Long id) {
    groceryItemService.deleteGroceryItem(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();    
}

}
