package com.nab.assignment.ecom.kafka.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class MessageSender {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public MessageSender(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public ListenableFuture<SendResult<String, Object>> sendMessage(String topic, Object msg) {
        return kafkaTemplate.send(topic, msg);
    }
}
