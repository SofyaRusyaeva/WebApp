package com.example.WebApp.dto;

import com.example.WebApp.model.ProductCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDto {

    @NotBlank(message = "Name can't be blank")
    String name;

    String description;

    @Enumerated(EnumType.STRING)
    ProductCategory category;

    @NotNull(message = "Price can't be null")
    @Positive(message = "Price must be positive")
    BigDecimal price;

    @NotNull(message = "Brand id can't be null")
    Long brandId;
}
