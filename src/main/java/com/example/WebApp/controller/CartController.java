package com.example.WebApp.controller;


import com.example.WebApp.config.JwtProvider;
import com.example.WebApp.dto.CartDto;
import com.example.WebApp.model.Cart;
import com.example.WebApp.model.Orders;
import com.example.WebApp.service.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shop/cart")
public class CartController {

    CartService cartService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/createOrder")
    public ResponseEntity<Orders> createOrderFromCart() {
        Orders order = cartService.createOrderFromCart();
        return ResponseEntity.ok(order);
    }

//    @PostMapping()
//    public ResponseEntity<Cart> addCart(@Valid @RequestBody CartDto cart) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.save(cart));
//    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<List<Cart>> getCarts() {
        return ResponseEntity.ok(cartService.findAll());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me")
    public ResponseEntity<CartDto> getCartItems() {
        return ResponseEntity.ok(cartService.findMyCart());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getUserCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.findByUserId(userId));
    }

//    @PutMapping("/{cartId}")
//    public ResponseEntity<Cart> updateCart(@Valid @RequestBody CartDto cart, @PathVariable Long cartId) {
//        return ResponseEntity.ok(cartService.update(cart, cartId));


    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/me")
    public ResponseEntity<?> clearCart () {
        cartService.clearCart();
        return ResponseEntity.noContent().build();
    }
}
