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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/shop/brands")
public class BrandController {

    BrandService brandService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public String getBrands(Model model) {
        model.addAttribute("brands", brandService.findAll());
        return "brands";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    @ResponseBody
    public ResponseEntity<Brand> addBrand(@Valid @RequestBody BrandDto brand) {
        return ResponseEntity.status(HttpStatus.CREATED).body(brandService.save(brand));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{brandId}")
    @ResponseBody
    public void updateBrand(@Valid @RequestBody BrandDto brand, @PathVariable Long brandId) {
        brandService.update(brand, brandId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{brandId}")
    @ResponseBody
    public void deleteBrand (@PathVariable Long brandId) {
        brandService.delete(brandId);
    }
}

