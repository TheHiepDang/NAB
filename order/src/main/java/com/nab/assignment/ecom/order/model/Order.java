package com.nab.assignment.ecom.order.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Entity
@Data
@Table(name = "Order_Table")
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @ElementCollection
    private Map<Long, Integer> cart = new ConcurrentHashMap<>();//productID, quantity

    private OrderStatusEnum status = OrderStatusEnum.NEW;

    @Column(name = "update_at")
    private Date updatedAt = new Date();
}
