package com.example.WebApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Entity
@Table(name = "brand")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long brandId;

    String name;
    String country;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    @JsonManagedReference
    @JsonIgnore
    List<Product> products;
}
