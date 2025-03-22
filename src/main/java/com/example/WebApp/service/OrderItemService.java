package com.example.WebApp.service;

import com.example.WebApp.dto.OrderItemDto;
import com.example.WebApp.exeption.ObjectSaveException;
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
        try {
            return orderItemRepository.save(orderItem);
        } catch (Exception e) {
            throw new ObjectSaveException("Error saving brand");
        }
    }

    public OrderItem update(OrderItemDto newOrderItem, Long id) {
        OrderItem oldOrderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found with id: " + id));

        Product product = productRepository.findById(newOrderItem.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + newOrderItem.getProductId()));

        Orders order = ordersRepository.findById(newOrderItem.getProductId())
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + newOrderItem.getOrderId()));

        oldOrderItem.setOrder(order);
        oldOrderItem.setProduct(product);
        oldOrderItem.setQuantity(newOrderItem.getQuantity());

        return orderItemRepository.save(oldOrderItem);
    }

    public void delete(Long orderItemId) {
        orderItemRepository.deleteById(orderItemId);
    }
}
