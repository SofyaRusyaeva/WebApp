package com.example.WebApp.service;

import com.example.WebApp.mapper.Mapper;
import com.example.WebApp.model.CartItem;
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


//    public CartItem save(CartItemDto cartItemDto) {
//        Cart cart = cartRepository.findById(cartItemDto.getCartId())
//                .orElseThrow(() -> new RuntimeException("Cart not found"));
//        Product product = productRepository.findById(cartItemDto.getProductId())
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//
//        BigDecimal quantity = BigDecimal.valueOf(cartItemDto.getQuantity());
//        cart.setTotalPrice(cart.getTotalPrice().add(product.getPrice().multiply(quantity)));
//        CartItem cartItem = mapper.toCartItem(cartItemDto, cart, product);
//        try {
//            return cartItemRepository.save(cartItem);
//        } catch (Exception e) {
//            throw new ObjectSaveException("Error saving brand");
//        }
//    }

    public CartItem update(Long id, boolean increase) {
        CartItem oldCartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CartItem not found with id: " + id));

        if (increase)
            oldCartItem.setQuantity(oldCartItem.getQuantity() + 1);
        else
            if (oldCartItem.getQuantity() > 0)
                oldCartItem.setQuantity(oldCartItem.getQuantity() - 1);

        return cartItemRepository.save(oldCartItem);
    }


    public void delete(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}
