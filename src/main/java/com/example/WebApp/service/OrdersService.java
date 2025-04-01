package com.example.WebApp.service;

import com.example.WebApp.dto.OrdersDto;
import com.example.WebApp.exeption.ObjectNotFoundException;
import com.example.WebApp.mapper.Mapper;
import com.example.WebApp.model.*;
import com.example.WebApp.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;
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
    Mapper mapper;

//    public List<Orders> findAll() {
//        return ordersRepository.findAll();
//    }

    public List<Orders> sortAndFilter(
            String sortBy, String sortDirection,
            List<OrderStatus> statuses,
            LocalDate startDate, LocalDate endDate,
            BigDecimal minPrice, BigDecimal maxPrice) {

        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        if ("desc".equalsIgnoreCase(sortDirection))
            sort = Sort.by(Sort.Direction.DESC, sortBy);

        if(statuses != null && !statuses.isEmpty() && startDate != null && endDate != null && minPrice != null && maxPrice != null)
            return ordersRepository.findByTotalPriceBetweenAndDateBetweenAndStatusIn(minPrice, maxPrice, startDate, endDate, statuses, sort);
        if (statuses != null && !statuses.isEmpty() && startDate != null && endDate != null)
            return ordersRepository.findByStatusInAndDateBetween(statuses, startDate, endDate, sort);
        if(minPrice != null && maxPrice != null && startDate != null && endDate != null)
            return ordersRepository.findByTotalPriceBetweenAndDateBetween(minPrice, maxPrice, startDate, endDate, sort);
        if(statuses != null && minPrice != null && maxPrice != null)
            return ordersRepository.findByTotalPriceBetweenAndStatusIn(minPrice, maxPrice, statuses, sort);
        if (statuses != null && !statuses.isEmpty())
            return ordersRepository.findByStatusIn(statuses, sort);
        if (startDate != null && endDate != null)
            return ordersRepository.findByDateBetween(startDate, endDate, sort);
        if (minPrice != null && maxPrice != null)
            return ordersRepository.findByTotalPriceBetween(minPrice, maxPrice, sort);
        return ordersRepository.findAll();
    }

    public List<Orders> findByUserId (Long userId){
        return ordersRepository.findByUser_UserId(userId);
    }

//    public Orders findByOrderId(Long orderId) {
//        return ordersRepository.findById(orderId)
//                .orElseThrow(() -> new ObjectNotFoundException(String.format("Order %s not found", orderId)));
//    }

//    public Orders save(OrdersDto orderDto) {
//        Users user = usersRepository.findById(orderDto.getUserId())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        Orders order = mapper.toOrders(orderDto, user);
//        try {
//            return ordersRepository.save(order);
//        } catch (Exception e) {
//            throw new ObjectSaveException("Error saving order");
//        }
//    }

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

//    public void delete(Long ordersId) {
//        ordersRepository.deleteById(ordersId);
//    }
}
