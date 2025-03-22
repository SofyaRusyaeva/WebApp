package com.example.WebApp.controller;


import com.example.WebApp.dto.OrderItemDto;
import com.example.WebApp.model.OrderItem;
import com.example.WebApp.service.OrderItemService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shop/orderItem")
public class OrderItemController {

    OrderItemService orderItemService;

    @GetMapping()
    public ResponseEntity<List<OrderItem>> getOrderItems() {
        return ResponseEntity.ok(orderItemService.findAll());
    }

    @PostMapping()
    public ResponseEntity<OrderItem> addOrderItem(@Valid @RequestBody OrderItemDto orderItem) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderItemService.save(orderItem));
    }

    @PutMapping("/{orderItemId}")
    public ResponseEntity<OrderItem> updateOrderItem(@Valid @RequestBody OrderItemDto orderItem, @PathVariable Long orderItemId) {
        return ResponseEntity.ok(orderItemService.update(orderItem, orderItemId));
    }

    @DeleteMapping("/{orderItemId}")
    public ResponseEntity<?> deleteOrderItem (@PathVariable Long orderItemId) {
        orderItemService.delete(orderItemId);
        return ResponseEntity.ok(String.format("Order item %s deleted", orderItemId));
    }
}
