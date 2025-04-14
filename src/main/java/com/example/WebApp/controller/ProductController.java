package com.example.WebApp.controller;

import com.example.WebApp.dto.ProductDto;
import com.example.WebApp.dto.ProductResponseDto;
import com.example.WebApp.model.CartItem;
import com.example.WebApp.model.Product;
import com.example.WebApp.model.ProductCategory;
import com.example.WebApp.service.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shop/product")
public class ProductController {

    ProductService productService;

    @GetMapping()
    public ResponseEntity<List<ProductResponseDto>> findSortedAndFiltered(
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(required = false)List<ProductCategory> categories,
            @RequestParam(required = false)BigDecimal minPrice,
            @RequestParam(required = false)BigDecimal maxPrice,
            @RequestParam(required = false)List<String> brandNames) {
        return ResponseEntity.ok(productService.sortAndFilter(sortBy, sortDirection, categories, minPrice, maxPrice, brandNames));
    }

//    @GetMapping()
//    public ResponseEntity<List<Product>> getProductsSortedByName(
//            @RequestParam(defaultValue = "name") String sortBy,
//            @RequestParam(defaultValue = "asc") String sortDirection) {
//        return ResponseEntity.ok(productService.findAllSorted(sortBy, sortDirection));
//    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.findById(productId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<Product> addProduct(@Valid @RequestBody ProductDto product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{productId}")
    public ResponseEntity<CartItem> createCartItem(@PathVariable Long productId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.addProductToCart(productId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody ProductDto product, @PathVariable Long productId) {
        return ResponseEntity.ok(productService.update(product, productId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct (@PathVariable Long productId) {
        productService.delete(productId);
        return ResponseEntity.noContent().build();
    }
}
