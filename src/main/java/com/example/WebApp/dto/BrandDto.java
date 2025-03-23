package com.example.WebApp.dto;

import com.example.WebApp.model.Country;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrandDto {

    @NotBlank(message = "Name cant be null")
    String name;

    @Enumerated(EnumType.STRING)
    Country country;
}