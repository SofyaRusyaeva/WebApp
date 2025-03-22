package com.example.WebApp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDto {

    @NotBlank(message = "Name can't be blank")
    String name;

    String description;
    String category;

    @NotNull(message = "Price can't be null")
    Long price;

    @NotNull(message = "Brand id can't be null")
    Long brandId;
}
