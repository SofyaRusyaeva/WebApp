package com.example.WebApp.service;

import com.example.WebApp.dto.OrdersDto;
import com.example.WebApp.mapper.Mapper;
import com.example.WebApp.model.Orders;
import com.example.WebApp.model.Users;
import com.example.WebApp.repository.OrdersRepository;
import com.example.WebApp.repository.UsersRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrdersService {
    OrdersRepository ordersRepository;
    UsersRepository usersRepository;
    Mapper mapper;

    public List<Orders> findAll() { return ordersRepository.findAll(); }

    public Orders save(OrdersDto orderDto) {
        Users user = usersRepository.findById(orderDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Orders order = mapper.toOrders(orderDto, user);
        return ordersRepository.save(order);
    }
}
