package com.example.WebApp.service;

import com.example.WebApp.dto.OrdersDto;
import com.example.WebApp.exeption.ObjectNotFoundException;
import com.example.WebApp.exeption.ObjectSaveException;
import com.example.WebApp.mapper.Mapper;
import com.example.WebApp.model.Orders;
import com.example.WebApp.model.Users;
import com.example.WebApp.repository.OrdersRepository;
import com.example.WebApp.repository.UsersRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrdersService {
    OrdersRepository ordersRepository;
    UsersRepository usersRepository;
    Mapper mapper;

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
