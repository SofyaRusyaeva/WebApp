package com.example.WebApp.service;

import com.example.WebApp.model.CartItem;
import com.example.WebApp.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;
    public List<CartItem> findAll() { return cartItemRepository.findAll(); }
    public CartItem save(CartItem cartItem) { return cartItemRepository.save(cartItem);}
}
