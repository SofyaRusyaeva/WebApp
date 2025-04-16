package com.example.WebApp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CartDto {

    //@NotNull(message = "Total price can't be null")
    BigDecimal totalPrice;

    List<ItemResponseDto> cartItems;

//    @NotNull(message = "User id can't be null")
//    Long userId;
}