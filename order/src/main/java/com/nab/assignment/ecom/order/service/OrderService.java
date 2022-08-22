package com.nab.assignment.ecom.order.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nab.assignment.ecom.dto.FinalizedOrderDTO;
import com.nab.assignment.ecom.dto.OrderDTO;
import com.nab.assignment.ecom.order.model.Order;
import com.nab.assignment.ecom.order.model.OrderStatusEnum;
import com.nab.assignment.ecom.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class OrderService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final OrderRepository repository;
    private final ObjectMapper objectMapper;

    public void addProductToOrder(Long userId, Long productID, int quantity) {
        Optional<Order> order = repository.findByUserIdAndStatus(userId, OrderStatusEnum.NEW);
        order.ifPresentOrElse(existingOrder -> {
            existingOrder.getCart().computeIfPresent(productID, (k, v) -> v + quantity);
            existingOrder.getCart().computeIfAbsent(productID, v -> quantity);
            repository.save(existingOrder);
        }, () -> {
            Order newOrder = new Order();
            newOrder.setStatus(OrderStatusEnum.NEW);
            newOrder.setUserId(userId);
            newOrder.getCart().computeIfAbsent(productID, v -> quantity);
            repository.save(newOrder);
        });
    }

    public void checkout(Long orderId) {
        Optional<Order> order = repository.findById(orderId);
        order.ifPresentOrElse(existingOrder -> {
            if (OrderStatusEnum.NEW.equals(existingOrder.getStatus())) {
                OrderDTO orderDTO = objectMapper.convertValue(existingOrder, OrderDTO.class);
                //Check and reserve stock
                kafkaTemplate.send("NAB_CheckStock", orderDTO);
            } else {
                log.info("Sumthing wong.");
            }
        }, () -> {
            log.info("Sumthing wong.");
        });
    }

    public FinalizedOrderDTO finalizeOrder(FinalizedOrderDTO finalizedOrder) {
//        FinalizedOrderDTO finalizedOrderDTO = new FinalizedOrderDTO();
        repository.findById(finalizedOrder.getOrder().getId()).ifPresentOrElse(existingOrder -> {
            finalizedOrder.setOrder(objectMapper.convertValue(existingOrder, OrderDTO.class));
            existingOrder.setCart(finalizedOrder.getFinalizedOrder().getCart());
            existingOrder.setStatus(OrderStatusEnum.RESERVED);
            finalizedOrder.setFinalizedOrder(objectMapper.convertValue(existingOrder, OrderDTO.class));
        }, () -> {
            //Wrong
            log.info("Sumthing wong.");
        });
        return finalizedOrder;
    }
}
