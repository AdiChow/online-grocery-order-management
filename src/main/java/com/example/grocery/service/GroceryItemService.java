package com.example.grocery.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.grocery.entity.GroceryItem;
import com.example.grocery.exception.ResourceNotFoundException;
import com.example.grocery.repository.GroceryItemRepository;

import lombok.RequiredArgsConstructor;
@Service 
@RequiredArgsConstructor 
public class GroceryItemService {

 private final GroceryItemRepository groceryItemRepository;

 public GroceryItem createGroceryItem(GroceryItem groceryItem) {
     return groceryItemRepository.save(groceryItem);
 }

 public List<GroceryItem> getAllGroceryItems() {
     return groceryItemRepository.findAll();
 }

 public GroceryItem getGroceryItemById(Long id) {
     return groceryItemRepository.findById(id)
             .orElseThrow(() ->  new ResourceNotFoundException("Grocery item not found with id: " + id));
 }

 public GroceryItem updateGroceryItem(Long id, GroceryItem groceryItem) {
     GroceryItem existingGroceryItem = groceryItemRepository.findById(id)
             .orElseThrow(() -> new ResourceNotFoundException("Grocery item not found with id: " + id));
     existingGroceryItem.setName(groceryItem.getName());
     existingGroceryItem.setCategory(groceryItem.getCategory());
     existingGroceryItem.setPrice(groceryItem.getPrice());
     existingGroceryItem.setQuantity(groceryItem.getQuantity());

     return groceryItemRepository.save(existingGroceryItem);
 }

 public void deleteGroceryItem(Long id) {
     GroceryItem existingGroceryItem = groceryItemRepository.findById(id)
             .orElseThrow(() -> new ResourceNotFoundException("Grocery item not found with id: " + id));
     groceryItemRepository.delete(existingGroceryItem);
 }
}
