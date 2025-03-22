package com.example.WebApp.controller;

import com.example.WebApp.dto.ProductDto;
import com.example.WebApp.model.Product;
import com.example.WebApp.service.ProductService;
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
@RequestMapping("/api/shop/product")
public class ProductController {

    ProductService productService;

    @GetMapping()
    public ResponseEntity<List<Product>> getProductsSortedByName(
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        return ResponseEntity.ok(productService.findAllSorted(sortBy, sortDirection));
    }

    @PostMapping()
    public ResponseEntity<Product> addProduct(@Valid @RequestBody ProductDto product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody ProductDto product, @PathVariable Long productId) {
        return ResponseEntity.ok(productService.update(product, productId));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct (@PathVariable Long productId) {
        productService.delete(productId);
        return ResponseEntity.noContent().build();
    }
}
