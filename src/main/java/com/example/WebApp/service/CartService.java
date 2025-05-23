package com.example.WebApp.service;

import com.example.WebApp.config.JwtProvider;
import com.example.WebApp.dto.CartDto;
import com.example.WebApp.dto.ItemResponseDto;
import com.example.WebApp.exeption.ObjectNotFoundException;
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
import java.util.stream.Collectors;

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
    JwtProvider jwtProvider;


    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    public Cart findByUserId(Long userId) {
        return cartRepository.findByUser_UserId(userId)
                .orElseThrow(()-> new ObjectNotFoundException("Cart not found"));
    }

    public CartDto findMyCart() {
        Long userId= jwtProvider.getCurrentUserId();
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new ObjectNotFoundException("Cart not found"));
        List<ItemResponseDto> items = cartItemRepository.findByCart_CartIdOrderByCartItemIdAsc(cart.getCartId()).stream()
                .map(mapper::toCartItemResponse)
                .collect(Collectors.toList());
        return new CartDto(cart.getTotalPrice(), items);
    }

    public Cart save(Long userId) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setTotalPrice(BigDecimal.ZERO);
//                mapper.toCart(cartDto, user);
//        cart.setTotalPrice(BigDecimal.ZERO);
        return cartRepository.save(cart);
    }


    @Transactional
    public Orders createOrderFromCart() {
        Long userId= jwtProvider.getCurrentUserId();
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

            clearCart();

            return savedOrder;
    }

    private BigDecimal calculateTotalPrice(Cart cart) {
        return cart.getCartItems().stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private OrderItem createOrderItem(Orders order, CartItem cartItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
//        orderItem.setProduct(cartItem.getProduct());
        orderItem.setTotalPrice(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        orderItem.setProductName(cartItem.getProduct().getName());
        orderItem.setQuantity(cartItem.getQuantity());
        return orderItem;
    }

    @Transactional
    public void clearCart() {
        Long userId = jwtProvider.getCurrentUserId();
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new ObjectNotFoundException("Cart not found for user id: " + userId));

        cartItemRepository.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();

        cart.setTotalPrice(BigDecimal.ZERO);
        cartRepository.save(cart);
    }
}
