package com.nab.assignment.ecom.order.kafka.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nab.assignment.ecom.dto.FinalizedOrderDTO;
import com.nab.assignment.ecom.order.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
class KafkaListeners {

    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "NAB_FinalizeOrder", containerFactory = "kafkaListenerContainerFactory", groupId = "${spring.kafka.consumer.group-id}")
    @SendTo("NAB_NotifyOrderStatus")
    private FinalizedOrderDTO finalizeOrderListener(@Payload FinalizedOrderDTO order) {
        log.info("NAB_FinalizeOrder: {}", order);
        return orderService.finalizeOrder(order);
    }

    @KafkaListener(topics = "NAB_NotifyOrderStatus", containerFactory = "kafkaListenerContainerFactory", groupId = "${spring.kafka.consumer.group-id}")
    private void listener(@Payload FinalizedOrderDTO order) {
        log.info("NAB_NotifyOrderStatus: {}", order);
        System.out.println("Order finalized. Details: " + JSONObject.wrap(order));
        //TODO: Compare, show differences, changes
    }

}