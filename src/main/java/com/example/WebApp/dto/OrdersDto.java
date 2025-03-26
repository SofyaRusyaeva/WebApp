package com.example.WebApp.dto;

import com.example.WebApp.model.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrdersDto {

    @NotNull(message = "User id can't be null")
    Long userId;

    Long totalPrice;

    @NotNull(message = "Date can't be null")
    LocalDate date;

//    @NotBlank(message = "Status can't blank")
    @Enumerated(EnumType.STRING)
    OrderStatus status;
}
