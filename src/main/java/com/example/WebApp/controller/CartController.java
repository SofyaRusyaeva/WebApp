package com.example.WebApp.controller;


import com.example.WebApp.dto.CartDto;
import com.example.WebApp.model.Cart;
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

    @PostMapping()
    public ResponseEntity<Cart> addCart(@Valid @RequestBody CartDto cart) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.save(cart));
    }

    @GetMapping()
    public ResponseEntity<List<Cart>> getCarts() {
        return ResponseEntity.ok(cartService.findAll());
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<?> deleteCart (@PathVariable Long cartId) {
        cartService.delete(cartId);
        return ResponseEntity.ok(String.format("Cart %s deleted", cartId));
    }
}
