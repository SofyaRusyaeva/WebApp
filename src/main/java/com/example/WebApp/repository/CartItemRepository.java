package com.example.WebApp.repository;

import com.example.WebApp.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository <CartItem, Long>{
}
