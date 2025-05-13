package com.example.WebApp.controller;

import com.example.WebApp.dto.ProductDto;
import com.example.WebApp.dto.ProductResponseDto;
import com.example.WebApp.dto.ProductUpdateDto;
import com.example.WebApp.model.Brand;
import com.example.WebApp.model.CartItem;
import com.example.WebApp.model.Product;
import com.example.WebApp.model.ProductCategory;
import com.example.WebApp.repository.BrandRepository;
import com.example.WebApp.service.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/shop/products")
public class ProductController {

    ProductService productService;
    BrandRepository brandRepository;

    @GetMapping()
    public String getProductPage(
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(required = false)List<ProductCategory> categories,
            @RequestParam(required = false)BigDecimal minPrice,
            @RequestParam(required = false)BigDecimal maxPrice,
            @RequestParam(required = false)List<String> brandNames,
            Model model,
            Authentication authentication) {
        List<ProductResponseDto> products = productService.sortAndFilter(sortBy, sortDirection, categories, minPrice, maxPrice, brandNames);
        model.addAttribute("products", products);
        model.addAttribute("categories", List.of(ProductCategory.values()));

        List<Brand> brands = brandRepository.findAll();

        List<String> brandNameList = brands.stream()
                .map(Brand::getName)
                .collect(Collectors.toList());
        model.addAttribute("brandNames", brandNameList);

        boolean isAdmin = false;
        if (authentication != null && authentication.isAuthenticated()) {
            isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        }
        model.addAttribute("isAdmin", isAdmin);

        return "products";
    }


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
    @ResponseBody
    public void createCartItem(@PathVariable Long productId) {
        productService.addProductToCart(productId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{productId}")
    @ResponseBody
    public void updateProduct(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam BigDecimal price,
            @PathVariable Long productId) {
        ProductUpdateDto updateDto = new ProductUpdateDto(name, description, price);
        productService.update(updateDto, productId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{productId}")
    @ResponseBody
    public void deleteProduct (@PathVariable Long productId) {
        productService.delete(productId);
    }
}
