package com.example.WebApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Entity
@Table(name = "cart")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long cartId;

    //@NotNull(message = "Total price can't be null")
    Long totalPrice;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    @JsonIgnore
    Users user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CartItem> cartItems;

    @JsonProperty("userId")
    public Long getUserId() {
        return user.getUserId();
    }
}