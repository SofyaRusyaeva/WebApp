package com.example.WebApp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CartDto {

    //@NotNull(message = "Total price can't be null")
    BigDecimal totalPrice;

    @NotNull(message = "User id can't be null")
    Long userId;
}
