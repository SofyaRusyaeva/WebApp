package com.example.WebApp.mapper;

import com.example.WebApp.dto.*;
import com.example.WebApp.model.*;
import com.example.WebApp.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Mapper {
    ProductRepository productRepository;

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

//    public Cart toCart(CartDto dto, Users user) {
//        Cart cart = new Cart();
//        cart.setUser(user);
//        cart.setTotalPrice(dto.getTotalPrice());
//        return cart;
//    }

    public Orders toOrders(OrdersDto dto, Users user) {
        Orders order = new Orders();
        order.setUser(user);
        order.setStatus(dto.getStatus());
        order.setDate(dto.getDate());
        order.setTotalPrice(dto.getTotalPrice());
        return order;
    }

//    public CartItem toCartItem(CartItemDto dto, Cart cart, Product product) {
//        CartItem cartItem = new CartItem();
//        cartItem.setCart(cart);
//        cartItem.setProduct(product);
//        cartItem.setQuantity(dto.getQuantity());
//        return cartItem;
//    }

//    public OrderItem toOrderItem(OrderItemDto dto, Orders order, Product product) {
//        OrderItem orderItem = new OrderItem();
//        orderItem.setOrder(order);
//        orderItem.setProduct(product);
//        orderItem.setQuantity(dto.getQuantity());
//        return orderItem;
//    }

    public Users toUsers(UsersDto dto) {
        Users user = new Users();
        user.setUserName(dto.getUserName());
        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(Role.ROLE_USER);
        return user;
    }

    public ItemResponseDto toCartItemResponse(CartItem cartItem) {
        ItemResponseDto cartItemResponseDto = new ItemResponseDto();
        cartItemResponseDto.setItemId(cartItem.getCartItemId());
        cartItemResponseDto.setQuantity(cartItem.getQuantity());
        Product product = cartItem.getProduct();
        cartItemResponseDto.setProductName(product.getName());
        cartItemResponseDto.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        return cartItemResponseDto;
    }

    public ItemResponseDto toOrderItemResponse(OrderItem orderItem) {
        ItemResponseDto orderItemResponseDto = new ItemResponseDto();
        orderItemResponseDto.setQuantity(orderItem.getQuantity());
        Product product = orderItem.getProduct();
        orderItemResponseDto.setProductName(product.getName());
        orderItemResponseDto.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
        return orderItemResponseDto;
    }

    public ProductResponseDto toProductResponse(Product product) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setName(product.getName());
        productResponseDto.setDescription(product.getDescription());
        productResponseDto.setCategory(product.getCategory());
        productResponseDto.setPrice(product.getPrice());
        Brand brand = product.getBrand();
        productResponseDto.setBrandName(brand.getName());
        productResponseDto.setId(product.getProductId());
        return productResponseDto;
    }
}