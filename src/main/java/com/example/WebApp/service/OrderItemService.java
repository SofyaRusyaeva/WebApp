package com.example.WebApp.service;

import com.example.WebApp.model.OrderItem;
import com.example.WebApp.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;
    public List<OrderItem> findAll() { return orderItemRepository.findAll(); }
    public OrderItem save(OrderItem orderItem) { return orderItemRepository.save(orderItem);}
}
