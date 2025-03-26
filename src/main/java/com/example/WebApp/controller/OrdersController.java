package com.example.WebApp.controller;

import com.example.WebApp.dto.OrdersDto;
import com.example.WebApp.model.Orders;
import com.example.WebApp.service.OrdersService;
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
@RequestMapping("/api/shop/orders")
public class OrdersController {

    OrdersService ordersService;

    @PostMapping("/from_cart/{cartId}")
    public ResponseEntity<Orders> createOrder(@PathVariable Long cartId) {
        return ResponseEntity.ok(ordersService.createOrderFromCart(cartId));
    }

    @GetMapping()
    public ResponseEntity<List<Orders>> getOrders() {
        return ResponseEntity.ok(ordersService.findAll());
    }

    @GetMapping("/{ordersId}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Long ordersId) {
        return ResponseEntity.ok(ordersService.findByOrderId(ordersId));
    }

    @PostMapping()
    public ResponseEntity<Orders> addOrders(@Valid @RequestBody OrdersDto orders) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ordersService.save(orders));
    }

    @PutMapping("/{ordersId}")
    public ResponseEntity<Orders> updateOrder(@Valid @RequestBody OrdersDto order, @PathVariable Long ordersId) {
        return ResponseEntity.ok(ordersService.update(order, ordersId));
    }

    @DeleteMapping("/{ordersId}")
    public ResponseEntity<?> deleteOrders(@PathVariable Long ordersId) {
        ordersService.delete(ordersId);
        return ResponseEntity.noContent().build();
    }
}
