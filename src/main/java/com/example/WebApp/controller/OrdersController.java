package com.example.WebApp.controller;

import com.example.WebApp.dto.OrdersDto;
import com.example.WebApp.model.Orders;
import com.example.WebApp.service.OrdersService;
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

    @GetMapping()
    public ResponseEntity<List<Orders>> getOrders() {
        return ResponseEntity.ok(ordersService.findAll());
    }

    @PostMapping()
    public ResponseEntity<Orders> addOrders(@RequestBody OrdersDto orders) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ordersService.save(orders));
    }

    @DeleteMapping("/{ordersId}")
    public ResponseEntity<?> deleteOrders(@PathVariable Long ordersId) {
        ordersService.delete(ordersId);
        return ResponseEntity.ok(String.format("UOrderser %s deleted", ordersId));
    }
}
