package com.example.WebApp.repository;

import com.example.WebApp.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository <CartItem, Long>{
    List<CartItem> findByProduct_ProductId(Long productId);

    List<CartItem> findByCart_CartIdOrderByCartItemIdAsc(Long cartId);

    List<CartItem> findByCart_CartIdAndProduct_ProductId(Long cartId, Long productId);
}
