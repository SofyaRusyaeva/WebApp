package com.example.WebApp.service;

import com.example.WebApp.config.JwtProvider;
import com.example.WebApp.dto.ItemResponseDto;
import com.example.WebApp.exeption.ObjectNotFoundException;
import com.example.WebApp.mapper.Mapper;
import com.example.WebApp.model.OrderStatus;
import com.example.WebApp.model.Orders;
import com.example.WebApp.repository.OrderItemRepository;
import com.example.WebApp.repository.OrdersRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrdersService {
    OrdersRepository ordersRepository;
    OrderItemRepository orderItemRepository;
    Mapper mapper;
    JwtProvider jwtProvider;


    public List<Orders> sortAndFilter(
            String sortBy, String sortDirection,
            List<OrderStatus> statuses,
            LocalDate startDate, LocalDate endDate,
            BigDecimal minPrice, BigDecimal maxPrice) {

        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        if ("desc".equalsIgnoreCase(sortDirection))
            sort = Sort.by(Sort.Direction.DESC, sortBy);

        if (maxPrice == null && minPrice != null)
            maxPrice = new BigDecimal("1000000");
        if (minPrice == null && maxPrice != null)
            minPrice = BigDecimal.ZERO;

        if (endDate == null && startDate != null)
            startDate = LocalDate.now();

        if (startDate == null && endDate != null)
            endDate = LocalDate.of(2000, 1, 1);


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
        return ordersRepository.findAll(sort);
    }

    public List<Orders> findByUserId (){
        Long userId = jwtProvider.getCurrentUserId();
        return ordersRepository.findByUser_UserIdOrderByOrderIdDesc(userId);
    }

    public List<ItemResponseDto> findByOrderId(Long orderId) {
        return orderItemRepository.findByOrder_OrderId(orderId).stream()
                .map(mapper::toOrderItemResponse)
                .collect(Collectors.toList());
    }

    public List<ItemResponseDto> findByOrderIdAndUserId(Long orderId) {
        Long userId = jwtProvider.getCurrentUserId();
        return orderItemRepository.findByOrder_OrderIdAndOrder_User_UserId(orderId, userId)
                .stream()
                .map(mapper::toOrderItemResponse)
                .collect(Collectors.toList());
    }


    public Orders update(OrderStatus status, Long id) {
        Orders oldOrder = ordersRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Order %s not found", id)));
        oldOrder.setStatus(status);

        return ordersRepository.save(oldOrder);
    }
}
