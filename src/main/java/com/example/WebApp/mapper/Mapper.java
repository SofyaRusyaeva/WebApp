package com.example.WebApp.mapper;

import com.example.WebApp.dto.*;
import com.example.WebApp.model.*;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public Product toProduct(ProductDto dto, Brand brand) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategory());
        product.setPrice(dto.getPrice());
        product.setBrand(brand);
        return product;
    }

    public Brand toBrand(BrandDto dto) {
        Brand brand = new Brand();
        brand.setName(dto.getName());
        brand.setCountry(dto.getCountry());
        return brand;
    }

    public Cart toCart(CartDto dto, Users user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setTotalPrice(dto.getTotalPrice());
        return cart;
    }

    public Orders toOrders(OrdersDto dto, Users user) {
        Orders order = new Orders();
        order.setUser(user);
        order.setStatus(dto.getStatus());
        order.setDate(dto.getDate());
        order.setTotalPrice(dto.getTotalPrice());
        return order;
    }

    public CartItem toCartItem(CartItemDto dto, Cart cart, Product product) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(dto.getQuantity());
        return cartItem;
    }

    public OrderItem toOrderItem(OrderItemDto dto, Orders order, Product product) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(dto.getQuantity());
        return orderItem;
    }

    public Users toUsers(UsersDto dto, Cart cart) {
        Users user = new Users();
        user.setUserName(dto.getUserName());
        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());
        user.setCart(cart);
        user.setPassword(dto.getPassword());
        return user;
    }
}