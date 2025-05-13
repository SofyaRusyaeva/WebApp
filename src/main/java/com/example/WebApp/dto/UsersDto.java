package com.example.WebApp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsersDto {

    @NotBlank(message = "username должно быть заполнено")
    String userName;

    @NotBlank(message = "email должен быть заполнен")
    String email;

    @NotBlank(message = "password должен быть заполнен")
    String password;

    String phone;
}
