package com.example.WebApp.service;

import com.example.WebApp.dto.OrdersDto;
import com.example.WebApp.exeption.ObjectNotFoundException;
import com.example.WebApp.exeption.ObjectSaveException;
import com.example.WebApp.mapper.Mapper;
import com.example.WebApp.model.*;
import com.example.WebApp.repository.*;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrdersService {
    OrdersRepository ordersRepository;
    UsersRepository usersRepository;
    OrderItemRepository orderItemRepository;
    CartRepository cartRepository;
    CartItemRepository cartItemRepository;
    Mapper mapper;

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

            clearCart(userId);

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

    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new ObjectNotFoundException("Cart not found"));

         cartItemRepository.deleteAll(cart.getCartItems());
         cart.getCartItems().clear();

        cart.setTotalPrice(BigDecimal.ZERO);
        cartRepository.save(cart);
    }

    public List<Orders> findAll() {
        return ordersRepository.findAll();
    }

    public Orders findByOrderId(Long orderId) {
        return ordersRepository.findById(orderId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Order %s not found", orderId)));
    }

    public Orders save(OrdersDto orderDto) {
        Users user = usersRepository.findById(orderDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Orders order = mapper.toOrders(orderDto, user);
//        Long totalPrice = orderItemRepository.calculateTotalPriceByOrderId(order.getOrderId());
//        order.setTotalPrice(totalPrice);
        try {
            return ordersRepository.save(order);
        } catch (Exception e) {
            throw new ObjectSaveException("Error saving order");
        }
    }

    public Orders update(OrdersDto newOrder, Long id) {
        Orders oldOrder = ordersRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Order %s not found", id)));

        Users user = usersRepository.findById(newOrder.getUserId())
                .orElseThrow(() -> new ObjectNotFoundException(String.format("User %s not found", newOrder.getUserId())));

        oldOrder.setUser(user);
        oldOrder.setTotalPrice(newOrder.getTotalPrice());
        oldOrder.setDate(newOrder.getDate());
        oldOrder.setStatus(newOrder.getStatus());

        return ordersRepository.save(oldOrder);
    }

    public void delete(Long ordersId) {
        ordersRepository.deleteById(ordersId);
    }
}
