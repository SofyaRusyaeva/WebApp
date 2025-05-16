package com.example.WebApp.controller;

import com.example.WebApp.dto.ItemResponseDto;
import com.example.WebApp.model.OrderStatus;
import com.example.WebApp.model.Orders;
import com.example.WebApp.service.OrdersService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/shop/orders")
public class OrdersController {

    OrdersService ordersService;


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public String findSortedAndFiltered(
            @RequestParam(defaultValue = "orderId") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection,
            @RequestParam(required = false) List<OrderStatus> statuses,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            Model model) {
        model.addAttribute("orders", ordersService.sortAndFilter(sortBy, sortDirection, statuses, startDate, endDate, minPrice, maxPrice));
        return "ordersAdmin";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me")
    public String getUserOrders(Model model) {
        model.addAttribute("orders", ordersService.findByUserId());
        return "orders";
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
    @PatchMapping("/{orderId}")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody Map<String, String> request) {

        try {
            OrderStatus status = OrderStatus.valueOf(request.get("status"));
            Orders updatedOrder = ordersService.update(status, orderId);
            return ResponseEntity.ok(updatedOrder);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Неверный статус заказа");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ошибка сервера");
        }
    }
}
