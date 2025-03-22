package com.example.WebApp.service;

import com.example.WebApp.dto.CartItemDto;
import com.example.WebApp.mapper.Mapper;
import com.example.WebApp.model.Cart;
import com.example.WebApp.model.CartItem;
import com.example.WebApp.model.Product;
import com.example.WebApp.repository.CartItemRepository;
import com.example.WebApp.repository.CartRepository;
import com.example.WebApp.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartItemService {
    CartItemRepository cartItemRepository;
    CartRepository cartRepository;
    ProductRepository productRepository;
    Mapper mapper;

    public List<CartItem> findAll() { return cartItemRepository.findAll(); }

    public CartItem save(CartItemDto cartItemDto) {
        Cart cart = cartRepository.findById(cartItemDto.getCartId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        Product product = productRepository.findById(cartItemDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = mapper.toCartItem(cartItemDto, cart, product);
        return cartItemRepository.save(cartItem);
    }

    public CartItem update(CartItemDto newCartItem, Long id) {
        CartItem oldCartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CartItem not found with id: " + id));

        Product product = productRepository.findById(newCartItem.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + newCartItem.getProductId()));

        Cart cart = cartRepository.findById(newCartItem.getProductId())
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + newCartItem.getCartId()));

        oldCartItem.setCart(cart);
        oldCartItem.setProduct(product);
        oldCartItem.setQuantity(newCartItem.getQuantity());

        return cartItemRepository.save(oldCartItem);
    }

    public void delete(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}
