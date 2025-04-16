package com.example.WebApp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordDto {

    @NotNull(message = "Password can't be null")
    String oldPass;

    @NotNull(message = "Password can't be null")
    String newPass;
}
