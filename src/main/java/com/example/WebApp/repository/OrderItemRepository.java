package com.example.WebApp.repository;

import com.example.WebApp.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder_OrderId(Long orderId);

    List<OrderItem> findByOrder_OrderIdAndOrder_User_UserId(Long orderId, Long userId);
}
