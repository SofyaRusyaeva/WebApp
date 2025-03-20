package com.example.WebApp.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OrdersDto {
    Long userId;
    Long totalPrice;
    Date date;
    String status;
}
