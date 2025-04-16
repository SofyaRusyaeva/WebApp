package com.example.WebApp.controller;

import com.example.WebApp.config.JwtProvider;
import com.example.WebApp.dto.ItemResponseDto;
import com.example.WebApp.model.OrderStatus;
import com.example.WebApp.model.Orders;
import com.example.WebApp.service.OrdersService;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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


    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me")
    public ResponseEntity<List<Orders>> getUserOrders() {
        return ResponseEntity.ok(ordersService.findByUserId());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<ItemResponseDto>> getOrderItems(@PathVariable Long orderId) {
        return ResponseEntity.ok(ordersService.findByOrderId(orderId));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me/{orderId}")
    public ResponseEntity<List<ItemResponseDto>> getMyOrderItems(@PathVariable Long orderId) {
        return ResponseEntity.ok(ordersService.findByOrderIdAndUserId(orderId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{ordersId}")
    public ResponseEntity<Orders> updateOrders(@RequestBody @NotNull OrderStatus status, @PathVariable Long ordersId) {
        return ResponseEntity.ok(ordersService.update(status, ordersId));
    }
}
