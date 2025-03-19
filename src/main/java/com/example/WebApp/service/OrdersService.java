package com.example.WebApp.service;

import com.example.WebApp.model.Orders;
import com.example.WebApp.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;
    public List<Orders> findAll() { return ordersRepository.findAll(); }
    public Orders save(Orders orders) { return ordersRepository.save(orders);}
}
