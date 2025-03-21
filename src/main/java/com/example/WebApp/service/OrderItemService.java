package com.example.WebApp.service;

import com.example.WebApp.dto.OrderItemDto;
import com.example.WebApp.mapper.Mapper;
import com.example.WebApp.model.OrderItem;
import com.example.WebApp.model.Orders;
import com.example.WebApp.model.Product;
import com.example.WebApp.repository.OrderItemRepository;
import com.example.WebApp.repository.OrdersRepository;
import com.example.WebApp.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderItemService {

    OrderItemRepository orderItemRepository;
    OrdersRepository ordersRepository;
    ProductRepository productRepository;
    Mapper mapper;

    public List<OrderItem> findAll() { return orderItemRepository.findAll(); }

    public OrderItem save(OrderItemDto orderItemDto) {
        Orders order = ordersRepository.findById(orderItemDto.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));
        Product product = productRepository.findById(orderItemDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        OrderItem orderItem = mapper.toOrderItem(orderItemDto, order, product);
        return orderItemRepository.save(orderItem);
    }

    public void delete(Long orderItemId) {
        orderItemRepository.deleteById(orderItemId);
    }
}
