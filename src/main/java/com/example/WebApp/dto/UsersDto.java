package com.example.WebApp.dto;

import lombok.Data;

@Data
public class UsersDto {
    String userName;
    String email;
    String password;
    String phone;
    Long cartId;
}
