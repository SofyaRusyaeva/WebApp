package com.example.WebApp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsersDto {

    @NotBlank(message = "username can't be blank")
    String userName;

    @NotBlank(message = "email can't be blank")
    String email;

    @NotBlank(message = "password can't be blank")
    String password;

    String phone;
}
