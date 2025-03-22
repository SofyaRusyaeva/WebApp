package com.example.WebApp.service;

import com.example.WebApp.dto.CartDto;
import com.example.WebApp.mapper.Mapper;
import com.example.WebApp.model.Cart;
import com.example.WebApp.model.Users;
import com.example.WebApp.repository.CartRepository;
import com.example.WebApp.repository.UsersRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartService {
    CartRepository cartRepository;
    UsersRepository usersRepository;
    Mapper mapper;

    public List<Cart> findAll() { return cartRepository.findAll(); }

    public Cart save(CartDto cartDto) {
        Users user = usersRepository.findById(cartDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = mapper.toCart(cartDto, user);
        return cartRepository.save(cart);
    }

    public Cart update(CartDto newCart, Long id) {
        Cart oldCart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + id));

        Users user = usersRepository.findById(newCart.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + newCart.getUserId()));

//        oldCart.setUser(user);
        oldCart.setTotalPrice(newCart.getTotalPrice());

        return cartRepository.save(oldCart);
    }

    public void delete(Long cartId) {
        usersRepository.deleteById(cartId);
    }
}
