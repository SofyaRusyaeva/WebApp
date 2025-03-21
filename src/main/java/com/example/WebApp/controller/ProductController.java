package com.example.WebApp.controller;

import com.example.WebApp.dto.ProductDto;
import com.example.WebApp.model.Brand;
import com.example.WebApp.model.Product;
import com.example.WebApp.service.ProductService;
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
@RequestMapping("/api/shop/product")
public class ProductController {

    ProductService productService;

    @GetMapping()
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/sortByName")
    public ResponseEntity<List<Product>> getProductsSortedByName(@RequestParam(defaultValue = "asc") String order) {
        if ("desc".equalsIgnoreCase(order)) {
            return ResponseEntity.ok(productService.getProductsSortedByNameDesc());
        }
        return ResponseEntity.ok(productService.getProductsSortedByNameAsc());
    }

    @GetMapping("/sortByPrice")
    public ResponseEntity<List<Product>> getProductsSortedByPrice(@RequestParam(defaultValue = "asc") String order) {
        if ("desc".equalsIgnoreCase(order)) {
            return ResponseEntity.ok(productService.getProductsSortedByPriceDesc());
        }
        return ResponseEntity.ok(productService.getProductsSortedByPriceAsc());
    }

    @PostMapping()
    public ResponseEntity<Product> addProduct(@RequestBody ProductDto product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct (@PathVariable Long productId) {
        productService.delete(productId);
        return ResponseEntity.ok(String.format("Product %s deleted", productId));
    }
}
