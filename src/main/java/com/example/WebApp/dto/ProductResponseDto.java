package com.example.WebApp.dto;

import com.example.WebApp.model.ProductCategory;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponseDto {
    String name;
    String description;
    ProductCategory category;
    BigDecimal price;
    String brandName;
}
