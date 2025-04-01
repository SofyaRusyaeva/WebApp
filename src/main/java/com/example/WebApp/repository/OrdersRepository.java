package com.example.WebApp.repository;

import com.example.WebApp.model.CartItem;
import com.example.WebApp.model.OrderStatus;
import com.example.WebApp.model.Orders;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUser_UserId(Long userId);

    List<Orders> findByDateBetween(LocalDate startDate, LocalDate endDate, Sort sort);

    List<Orders> findByStatusInAndDateBetween(List<OrderStatus> statuses, LocalDate startDate, LocalDate endDate, Sort sort);

    List<Orders> findByStatusIn(List<OrderStatus> statuses, Sort sort);

    List<Orders> findByTotalPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Sort sort);

    List<Orders> findByTotalPriceBetweenAndDateBetween(BigDecimal minPrice, BigDecimal maxPrice, LocalDate startDate, LocalDate endDate, Sort sort);

    List<Orders>  findByTotalPriceBetweenAndStatusIn(BigDecimal minPrice, BigDecimal maxPrice, List<OrderStatus> statuses, Sort sort);

    List<Orders> findByTotalPriceBetweenAndDateBetweenAndStatusIn(BigDecimal minPrice, BigDecimal maxPrice, LocalDate startDate, LocalDate endDate, List<OrderStatus> statuses, Sort sort);
}