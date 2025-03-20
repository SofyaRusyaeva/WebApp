package com.example.WebApp.dto;


import lombok.Data;

@Data
public class CartItemDto {
    Long cartId;
    Long productId;
    Long quantity;
}
