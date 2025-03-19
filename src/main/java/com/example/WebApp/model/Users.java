package com.example.WebApp.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;

    String userName;
    String email;
    String password;
    String phone;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Orders> orders;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    Cart cart;
}