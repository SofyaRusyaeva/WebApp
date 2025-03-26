package com.example.WebApp.repository;

import com.example.WebApp.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
//    @Query("""
//    SELECT COALESCE(SUM(p.price * oi.quantity), 0)
//    FROM OrderItem oi
//    JOIN oi.product p
//    WHERE oi.order.id = :orderId
//    """)
//
//    Long calculateTotalPriceByOrderId(@Param("orderId") Long orderId);
}
