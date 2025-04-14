package com.example.WebApp.controller;

import com.example.WebApp.dto.BrandDto;
import com.example.WebApp.model.Brand;
import com.example.WebApp.service.BrandService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shop/brand")
public class BrandController {

    BrandService brandService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<List<Brand>> getBrands() {
        return ResponseEntity.ok(brandService.findAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<Brand> addBrand(@Valid @RequestBody BrandDto brand) {
        return ResponseEntity.status(HttpStatus.CREATED).body(brandService.save(brand));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{brandId}")
    public ResponseEntity<Brand> updateBrand(@Valid @RequestBody BrandDto brand, @PathVariable Long brandId) {
        return ResponseEntity.ok(brandService.update(brand, brandId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{brandId}")
    public ResponseEntity<?> deleteBrand (@PathVariable Long brandId) {
        brandService.delete(brandId);
        return ResponseEntity.noContent().build();
    }
}

