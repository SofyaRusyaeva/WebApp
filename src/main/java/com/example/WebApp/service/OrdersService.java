package com.example.WebApp.service;

import com.example.WebApp.dto.OrdersDto;
import com.example.WebApp.exeption.ObjectNotFoundException;
import com.example.WebApp.exeption.ObjectSaveException;
import com.example.WebApp.mapper.Mapper;
import com.example.WebApp.model.*;
import com.example.WebApp.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

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

    public Orders createOrderFromCart(Long cartId) {

        // Получаем корзину пользователя
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Получаем пользователя
        Users user = usersRepository.findById(cart.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Получаем все товары в корзине
        List<CartItem> cartItems = cartItemRepository.findByCart_CartId(cartId);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        // Создаем новый заказ
        Orders order = new Orders();
        order.setUser(user);
        order.setDate(LocalDate.now());
        order.setStatus(OrderStatus.in_progress);
        order.setTotalPrice(0L);

        Orders savedOrder = ordersRepository.save(order);

        long totalPrice = 0L;

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());

            long itemTotalPrice = product.getPrice() * cartItem.getQuantity();
            totalPrice += itemTotalPrice;

            orderItemRepository.save(orderItem);
        }

        savedOrder.setTotalPrice(totalPrice);
        ordersRepository.save(savedOrder);

        cartItemRepository.deleteAll(cartItems);
        cart.setTotalPrice(0L);
        cartRepository.save(cart);

        return savedOrder;
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
