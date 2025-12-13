package com.web.demo.services;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaLogService kafkaLogService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate, KafkaLogService kafkaLogService) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaLogService = kafkaLogService;
    }

    public void send(String topic, String key, Object payload) {
        try {
            String json = objectMapper.writeValueAsString(payload);
            String messageId = UUID.randomUUID().toString();

            kafkaLogService.logRequest(messageId, topic, key, json);

            CompletableFuture<SendResult<String, String>> future =
                    kafkaTemplate.send(topic, key, json);

            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    RecordMetadata meta = result.getRecordMetadata();
                    kafkaLogService.logSuccess(messageId, meta.offset(), meta.partition(), topic);
                } else {
                    kafkaLogService.logFailure(messageId, ex.getMessage());
                }
            });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
