package com.example.WebApp.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemResponseDto {
    String productName;
    Long quantity;
    BigDecimal totalPrice;
}
