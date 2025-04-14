package com.example.WebApp.controller;


import com.example.WebApp.model.CartItem;
import com.example.WebApp.service.CartItemService;
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
@RequestMapping("/api/shop/cartItem")
public class CartItemController {

    CartItemService cartItemService;

//    @GetMapping()
//    public ResponseEntity<List<CartItem>> getCartItems() {
//        return ResponseEntity.ok(cartItemService.findAll());
//    }

//    @PostMapping()
//    public ResponseEntity<CartItem> addCartItem(@Valid @RequestBody CartItemDto cartItem) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemService.save(cartItem));
//    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{cartItemId}/inc")
    public ResponseEntity<CartItem> increaseQuantity(@PathVariable Long cartItemId) {
        return ResponseEntity.ok(cartItemService.update(cartItemId, true));
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{cartItemId}/dec")
    public ResponseEntity<CartItem> decreaseQuantity(@PathVariable Long cartItemId) {
        return ResponseEntity.ok(cartItemService.update(cartItemId, false));
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<?> deleteCartItem (@PathVariable Long cartItemId) {
        cartItemService.delete(cartItemId);
        return ResponseEntity.noContent().build();
    }
}
