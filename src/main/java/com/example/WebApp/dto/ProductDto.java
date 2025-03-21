package com.example.WebApp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDto {
    String name;
    String description;
    String category;
    Long price;

    @NotNull(message = "Brand id can't be null")
    Long brandId;
}
