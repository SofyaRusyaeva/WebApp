package com.example.WebApp.service;

import com.example.WebApp.dto.CartDto;
import com.example.WebApp.exeption.ObjectNotFoundException;
import com.example.WebApp.exeption.ObjectSaveException;
import com.example.WebApp.mapper.Mapper;
import com.example.WebApp.model.*;
import com.example.WebApp.repository.*;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartService {
    CartRepository cartRepository;
    UsersRepository usersRepository;
    OrderItemRepository orderItemRepository;
    CartItemRepository cartItemRepository;
    OrdersRepository ordersRepository;
    Mapper mapper;

    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    public Cart findByUserId(Long userId) {
        return cartRepository.findByUser_UserId(userId)
                .orElseThrow(()-> new ObjectNotFoundException("Cart not found"));
    }

    public Cart save(CartDto cartDto) {
        Users user = usersRepository.findById(cartDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = mapper.toCart(cartDto, user);
        cart.setTotalPrice(BigDecimal.ZERO);
        try {
            return cartRepository.save(cart);
        } catch (Exception e) {
            throw new ObjectSaveException("Error saving brand");
        }
    }

//    public Cart update(CartDto newCart, Long id) {
//        Cart oldCart = cartRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + id));
//
//        Users user = usersRepository.findById(newCart.getUserId())
//                .orElseThrow(() -> new RuntimeException("User not found with id: " + newCart.getUserId()));
//
//        oldCart.setUser(user);
//        oldCart.setTotalPrice(newCart.getTotalPrice());
//
//        return cartRepository.save(oldCart);
//    }

//    public void delete(Long cartId) {
//        usersRepository.deleteById(cartId);
//    }

    @Transactional
    public Orders createOrderFromCart(Long userId) {
        try {
            Users user = usersRepository.findById(userId)
                    .orElseThrow(() -> new ObjectNotFoundException("User not found with id: " + userId));

            Cart cart = cartRepository.findByUser_UserId(userId)
                    .orElseThrow(() -> new ObjectNotFoundException("Cart not found for user id: " + userId));

            if (cart.getCartItems().isEmpty()) {
                throw new IllegalStateException("Cart is empty");
            }

            cart.getCartItems().forEach(item -> {
                if (item.getProduct() == null) {
                    throw new IllegalStateException("Product not found in cart item");
                }
                if (item.getQuantity() <= 0) {
                    throw new IllegalStateException("Invalid quantity for product: " + item.getProduct().getProductId());
                }
            });

            Orders order = new Orders();
            order.setUser(user);
            order.setDate(LocalDate.now());
            order.setStatus(OrderStatus.in_progress);
            order.setTotalPrice(calculateTotalPrice(cart));

            Orders savedOrder = ordersRepository.save(order);

            List<OrderItem> orderItems = cart.getCartItems().stream()
                    .map(cartItem -> createOrderItem(savedOrder, cartItem))
                    .toList();

            orderItemRepository.saveAll(orderItems);

            clearCart(cart.getCartId());

            return savedOrder;
        } catch (Exception e) {
            throw new ObjectSaveException("Failed to create order: " + e.getMessage());
        }
    }

    private BigDecimal calculateTotalPrice(Cart cart) {
        return cart.getCartItems().stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private OrderItem createOrderItem(Orders order, CartItem cartItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(cartItem.getProduct());
        orderItem.setQuantity(cartItem.getQuantity());
        return orderItem;
    }

    public void clearCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ObjectNotFoundException("Cart not found"));

        cartItemRepository.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();

        cart.setTotalPrice(BigDecimal.ZERO);
        cartRepository.save(cart);
    }
}
