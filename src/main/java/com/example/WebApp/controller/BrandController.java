package com.example.WebApp.controller;

import com.example.WebApp.dto.BrandDto;
import com.example.WebApp.model.Brand;
import com.example.WebApp.service.BrandService;
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
@RequestMapping("/api/shop/brand")
public class BrandController {

    BrandService brandService;

    @GetMapping()
    public ResponseEntity<List<Brand>> getBrands() {
        return ResponseEntity.ok(brandService.findAll());
    }

    @PostMapping()
    public ResponseEntity<Brand> addBrand(@RequestBody BrandDto brand) {
        return ResponseEntity.status(HttpStatus.CREATED).body(brandService.save(brand));
    }

    @DeleteMapping("/{brandId}")
    public ResponseEntity<?> deleteBrand (@PathVariable Long brandId) {
        brandService.delete(brandId);
        return ResponseEntity.ok(String.format("Brand %s deleted", brandId));
    }
}

