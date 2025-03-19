package com.example.WebApp.service;

import com.example.WebApp.model.Cart;
import com.example.WebApp.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    public List<Cart> findAll() { return cartRepository.findAll(); }
    public Cart save(Cart cart) { return cartRepository.save(cart);}
}
