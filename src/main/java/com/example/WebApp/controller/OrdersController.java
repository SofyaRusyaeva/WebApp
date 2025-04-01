package com.example.WebApp.controller;

import com.example.WebApp.dto.OrdersDto;
import com.example.WebApp.model.OrderStatus;
import com.example.WebApp.model.Orders;
import com.example.WebApp.service.OrdersService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
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
            @RequestParam(required = false)BigDecimal minPrice,
            @RequestParam(required = false)BigDecimal maxPrice) {
        return ResponseEntity.ok(ordersService.sortAndFilter(sortBy, sortDirection, statuses, startDate, endDate, minPrice, maxPrice));
    }


    @GetMapping("/{userId}")
    public ResponseEntity<List<Orders>> getUserOrders(@PathVariable Long userId) {
        return ResponseEntity.ok(ordersService.findByUserId(userId));
    }

//    @GetMapping("/{ordersId}")
//    public ResponseEntity<Orders> getOrderById(@PathVariable Long ordersId) {
//        return ResponseEntity.ok(ordersService.findByOrderId(ordersId));
//    }

//    @PostMapping()
//    public ResponseEntity<Orders> addOrders(@Valid @RequestBody OrdersDto orders) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(ordersService.save(orders));
//    }

    @PutMapping("/{ordersId}")
    public ResponseEntity<Orders> updateOrder(@Valid @RequestBody OrdersDto order, @PathVariable Long ordersId) {
        return ResponseEntity.ok(ordersService.update(order, ordersId));
    }

//    @DeleteMapping("/{ordersId}")
//    public ResponseEntity<?> deleteOrders(@PathVariable Long ordersId) {
//        ordersService.delete(ordersId);
//        return ResponseEntity.noContent().build();
//    }
}
