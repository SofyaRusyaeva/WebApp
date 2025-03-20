package com.example.WebApp.dto;

import lombok.Data;

@Data
public class ProductDto {
    private String name;
    private String description;
    private String category;
    private Long price;
    private Long brandId;
}
