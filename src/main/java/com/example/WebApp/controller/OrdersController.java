package com.example.WebApp.controller;

import com.example.WebApp.dto.ItemResponseDto;
import com.example.WebApp.model.OrderStatus;
import com.example.WebApp.model.Orders;
import com.example.WebApp.service.OrdersService;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shop/orders")
public class OrdersController {

    OrdersService ordersService;

    @GetMapping()
    public ResponseEntity<List<Orders>> findSortedAndFiltered(
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(required = false) List<OrderStatus> statuses,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice) {
        return ResponseEntity.ok(ordersService.sortAndFilter(sortBy, sortDirection, statuses, startDate, endDate, minPrice, maxPrice));
    }


    @GetMapping("/{userId}")
    public ResponseEntity<List<Orders>> getUserOrders(@PathVariable Long userId) {
        return ResponseEntity.ok(ordersService.findByUserId(userId));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<ItemResponseDto>> getOrderItems(@PathVariable Long orderId) {
        return ResponseEntity.ok(ordersService.findByOrderId(orderId));
    }

    @GetMapping("/{userId}/{orderId}")
    public ResponseEntity<List<ItemResponseDto>> getOrderItems(@PathVariable Long userId, @PathVariable Long orderId) {
        return ResponseEntity.ok(ordersService.findByOrderIdAndUserId(userId, orderId));
    }

    @PatchMapping("/{ordersId}")
    public ResponseEntity<Orders> updateOrders(@RequestBody @NotNull OrderStatus status, @PathVariable Long ordersId) {
        return ResponseEntity.ok(ordersService.update(status, ordersId));
    }
}
