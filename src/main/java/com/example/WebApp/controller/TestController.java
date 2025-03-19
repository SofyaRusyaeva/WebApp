package com.example.WebApp.controller;

import com.example.WebApp.model.*;
import com.example.WebApp.service.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shop")
public class TestController {
    BrandService brandService;
    CartItemService cartItemService;
    CartService cartService;
    OrderItemService orderItemService;
    OrdersService ordersService;
    ProductService productService;
    UsersService usersService;
    @GetMapping("/brand")
    public ResponseEntity<List<Brand>> getBrands() {
        return ResponseEntity.ok(brandService.findAll());
    }

    @GetMapping("/cartItem")
    public ResponseEntity<List<CartItem>> getCartItems() {
        return ResponseEntity.ok(cartItemService.findAll());
    }

    @GetMapping("/cart")
    public ResponseEntity<List<Cart>> getCarts() {
        return ResponseEntity.ok(cartService.findAll());
    }

    @GetMapping("/orderItem")
    public ResponseEntity<List<OrderItem>> getOrderItems() {
        return ResponseEntity.ok(orderItemService.findAll());
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Orders>> getOrders() {
        return ResponseEntity.ok(ordersService.findAll());
    }

    @GetMapping("/product")
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/users")
    public ResponseEntity<List<Users>> getUsers() {
        return ResponseEntity.ok(usersService.findAll());
    }

    @PostMapping("/brand")
    public ResponseEntity<Brand> addBrand(@RequestBody Brand brand) {
        return ResponseEntity.status(HttpStatus.CREATED).body(brandService.save(brand));
    }

    @PostMapping("/cartItem")
    public ResponseEntity<CartItem> addCartItem(@RequestBody CartItem cartItem) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemService.save(cartItem));
    }

    @PostMapping("/cart")
    public ResponseEntity<Cart> addCart(@RequestBody Cart cart) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.save(cart));
    }

    @PostMapping("/orderItem")
    public ResponseEntity<OrderItem> addOrderItem(@RequestBody OrderItem orderItem) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderItemService.save(orderItem));
    }

    @PostMapping("/orders")
    public ResponseEntity<Orders> addOrders(@RequestBody Orders orders) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ordersService.save(orders));
    }

    @PostMapping("/product")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }

    @PostMapping("/users")
    public ResponseEntity<Users> addUsers(@RequestBody Users users) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usersService.save(users));
    }
}
