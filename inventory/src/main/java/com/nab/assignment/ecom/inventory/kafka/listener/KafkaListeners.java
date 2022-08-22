package com.nab.assignment.ecom.inventory.kafka.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nab.assignment.ecom.dto.FinalizedOrderDTO;
import com.nab.assignment.ecom.dto.OrderDTO;
import com.nab.assignment.ecom.inventory.kafka.service.KafkaInventoryService;
import com.nab.assignment.ecom.inventory.service.InventoryService;
import com.nab.assignment.ecom.kafka.service.MessageSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
@Slf4j
@AllArgsConstructor
class KafkaListeners {

    private final InventoryService inventoryService;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "NAB_CheckStock", containerFactory = "kafkaListenerContainerFactory", groupId = "${spring.kafka.consumer.group-id}")
    public void isInStock(@Payload OrderDTO order) throws JsonProcessingException {
        log.info("NAB_CheckStock: {}", order);
        FinalizedOrderDTO finalizedOrderDTO = new FinalizedOrderDTO();
        finalizedOrderDTO.setOrder(order);
        AtomicReference<BigDecimal> finalPrice = new AtomicReference<>();
        Map<Long, Integer> updatedCart = order.getCart().entrySet().parallelStream()
                .filter(e -> inventoryService.subtractFromStock(e.getKey(), e.getValue(), finalPrice))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        order.setCart(updatedCart);
        finalizedOrderDTO.setFinalizedOrder(order);
        finalizedOrderDTO.setFinalPrice(finalPrice.get());

        kafkaTemplate.send("NAB_FinalizeOrder", finalizedOrderDTO);
    }
}