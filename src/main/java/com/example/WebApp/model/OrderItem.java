package com.example.WebApp.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "order_item")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long orderItemId;

    @NotNull(message = "Quantity can't be null")
    Long quantity;

    @NotBlank(message = "Name can't be blank")
    String productName;

    @NotNull(message = "Price can't be null")
    BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private Orders order;

//    @ManyToOne
//    @JoinColumn(name = "product_id", nullable = false)
//    @JsonIgnore
//    private Product product;

    @JsonProperty("orderId")
    public Long getOrderId() {
        return order.getOrderId();
    }

//    @JsonProperty("productId")
//    public Long getProductId() {
//        return product.getProductId();
//    }
}
