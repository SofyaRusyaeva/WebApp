package com.example.WebApp.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemDto {

    @NotNull(message = "Cart id can't be null")
    Long cartId;

    @NotNull(message = "Product id can't be null")
    Long productId;

    Long quantity;
}
