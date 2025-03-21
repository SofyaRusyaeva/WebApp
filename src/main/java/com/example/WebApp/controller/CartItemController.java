package com.example.WebApp.controller;


import com.example.WebApp.dto.CartItemDto;
import com.example.WebApp.model.CartItem;
import com.example.WebApp.service.CartItemService;
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
@RequestMapping("/api/shop/cartItem")
public class CartItemController {

    CartItemService cartItemService;

    @GetMapping()
    public ResponseEntity<List<CartItem>> getCartItems() {
        return ResponseEntity.ok(cartItemService.findAll());
    }

    @PostMapping()
    public ResponseEntity<CartItem> addCartItem(@RequestBody CartItemDto cartItem) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemService.save(cartItem));
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<?> deleteCartItem (@PathVariable Long cartItemId) {
        cartItemService.delete(cartItemId);
        return ResponseEntity.ok(String.format("Cart item %s deleted", cartItemId));
    }
}
