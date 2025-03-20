package com.example.WebApp.service;

import com.example.WebApp.dto.ProductDto;
import com.example.WebApp.mapper.Mapper;
import com.example.WebApp.model.Brand;
import com.example.WebApp.model.Product;
import com.example.WebApp.repository.BrandRepository;
import com.example.WebApp.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {

    ProductRepository productRepository;
    BrandRepository brandRepository;
    Mapper mapper;


    public List<Product> findAll() { return productRepository.findAll(); }
    public Product save(ProductDto productDto) {
        Brand brand = brandRepository.findById(productDto.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found with id: " + productDto.getBrandId()));

        Product product = mapper.toProduct(productDto, brand);
        return productRepository.save(product);
    }
}
