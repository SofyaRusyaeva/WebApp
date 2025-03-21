package com.example.WebApp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartDto {

    @NotNull(message = "Total price can't be null")
    Long totalPrice;

    @NotNull(message = "User id can't be null")
    Long userId;
}
