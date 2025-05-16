package com.example.WebApp.service;

import com.example.WebApp.config.JwtProvider;
import com.example.WebApp.exeption.ObjectNotFoundException;
import com.example.WebApp.mapper.Mapper;
import com.example.WebApp.model.Cart;
import com.example.WebApp.model.CartItem;
import com.example.WebApp.repository.CartItemRepository;
import com.example.WebApp.repository.CartRepository;
import com.example.WebApp.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartItemService {
    CartItemRepository cartItemRepository;
    CartRepository cartRepository;
    JwtProvider jwtProvider;

    public List<CartItem> findAll() { return cartItemRepository.findAll(); }

    public CartItem update(Long id, boolean increase) {
        CartItem oldCartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CartItem not found with id: " + id));
        Cart cart = cartRepository.findById(oldCartItem.getCartId())
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + oldCartItem.getCartId()));
        if(!Objects.equals(jwtProvider.getCurrentUserId(), cart.getUserId()))
            throw new ObjectNotFoundException("You don't have cart item with id: " + id);
        if (increase)
            oldCartItem.setQuantity(oldCartItem.getQuantity() + 1);
        else {
            if (oldCartItem.getQuantity() > 1)
                oldCartItem.setQuantity(oldCartItem.getQuantity() - 1);
        }

        BigDecimal newSum = calculateTotalPrice(cart);
        cart.setTotalPrice(newSum);
        cartRepository.save(cart);
        return cartItemRepository.save(oldCartItem);
    }

    private BigDecimal calculateTotalPrice(Cart cart) {
        return cart.getCartItems().stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void delete(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("CartItem not found with id: " + cartItemId));

        Cart cart = cartRepository.findById(cartItem.getCartId())
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartItem.getCartId()));

        cartItemRepository.deleteById(cartItemId);

        BigDecimal newSum = calculateTotalPrice(cart);
        cart.setTotalPrice(newSum);
        cartRepository.save(cart);
    }
}
