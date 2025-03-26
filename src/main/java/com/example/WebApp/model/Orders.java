package com.example.WebApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long orderId;

    Long totalPrice;

    @NotNull(message = "Date can't be null")
    LocalDate date;

    @NotBlank(message = "Status can't blank")
    @Enumerated(EnumType.STRING)
    OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private Users user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderItem> orderItems;

    @JsonProperty("userId")
    public Long getUserId() {
        return user.getUserId();
    }
}