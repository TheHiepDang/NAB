package com.nab.assignment.ecom.order.repository;

import com.nab.assignment.ecom.order.model.Order;
import com.nab.assignment.ecom.order.model.OrderStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByUserIdAndStatus(Long userId, OrderStatusEnum Status);
}
