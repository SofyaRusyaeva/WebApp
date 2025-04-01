package com.example.WebApp.controller;


import com.example.WebApp.dto.CartDto;
import com.example.WebApp.model.Cart;
import com.example.WebApp.model.Orders;
import com.example.WebApp.service.CartService;
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
@RequestMapping("/api/shop/cart")
public class CartController {

    CartService cartService;

    @PostMapping("/createOrder/{userId}")
    public ResponseEntity<Orders> createOrderFromCart(@PathVariable Long userId) {
        Orders order = cartService.createOrderFromCart(userId);
        return ResponseEntity.ok(order);
    }

    @PostMapping()
    public ResponseEntity<Cart> addCart(@Valid @RequestBody CartDto cart) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.save(cart));
    }

    @GetMapping()
    public ResponseEntity<List<Cart>> getCarts() {
        return ResponseEntity.ok(cartService.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getUserCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.findByUserId(userId));
    }

//    @PutMapping("/{cartId}")
//    public ResponseEntity<Cart> updateCart(@Valid @RequestBody CartDto cart, @PathVariable Long cartId) {
//        return ResponseEntity.ok(cartService.update(cart, cartId));
//    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<?> clearCart (@PathVariable Long cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }
}
