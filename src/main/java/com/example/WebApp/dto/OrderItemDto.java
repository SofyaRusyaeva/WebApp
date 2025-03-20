package com.example.WebApp.dto;

import lombok.Data;

@Data
public class OrderItemDto {

    Long orderId;
    Long productId;
    Long quantity;
}
