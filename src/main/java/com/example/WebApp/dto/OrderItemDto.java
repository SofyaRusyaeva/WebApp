package com.example.WebApp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemDto {

    @NotNull(message = "Order id can't be null")
    Long orderId;

    @NotNull(message = "Product id can't be null")
    Long productId;

    Long quantity;
}
