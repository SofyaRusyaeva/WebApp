package com.example.WebApp.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsersDto {
    String userName;
    String email;
    String password;
    String phone;
}
