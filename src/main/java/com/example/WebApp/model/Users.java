package com.example.WebApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;

    @NotBlank(message = "Username can't be blank")
    String userName;

    @NotBlank(message = "Email can't be blank")
    @Email(message = "Invalid email format")
    @Column(unique = true)
    String email;

    @NotBlank(message = "Password can't be blank")
    String password;

    @Pattern(
            regexp = "^\\+7\\d{10}$",
            message = "Номер телефона должен быть в формате +7XXXXXXXXXX"
    )
    String phone;

    @Enumerated(EnumType.STRING)
    Role role = Role.ROLE_USER;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    List<Orders> orders;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    List<RefreshToken> token;
}