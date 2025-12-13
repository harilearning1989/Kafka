package com.web.demo.services;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class ReusableKafkaProducer {

   /* private final KafkaTemplate<String, String> kafkaTemplate;

    private final String dlqSuffix = "-dlq";

    public ReusableKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    private void attachCallback(CompletableFuture<SendResult<String, String>> future,
                                String topic, String key, String value,
                                String sourceLabel) {

        future.whenComplete((result, ex) -> {

            if (ex == null) { // SUCCESS
                var meta = result.getRecordMetadata();

                loggingService.log(
                        "INFO", sourceLabel,
                        "Message delivered",
                        meta.topic(), meta.partition(), meta.offset(),
                        key, value.length(), null
                );

            } else { // FAILURE
                loggingService.log(
                        "ERROR", sourceLabel,
                        "Delivery failed: " + ex.getMessage(),
                        topic, null, null,
                        key, value.length(), ex.toString()
                );
            }
        });
    }

    public void send(String topic, String key, String value) {

        loggingService.log("INFO", "Producer",
                "Sending message...",
                topic, null, null, key, value.length(), null);

        try {
            CompletableFuture<SendResult<String, String>> future =
                    kafkaTemplate.send(topic, key, value);

            attachCallback(future, topic, key, value, "ProducerSend");

        } catch (Exception e) {

            loggingService.log("ERROR", "Producer",
                    "Immediate exception: " + e.getMessage(),
                    topic, null, null, key, value.length(), e.toString());

            sendToDlq(topic, key, value, e.getMessage());
        }
    }

    private void sendToDlq(String topic, String key, String value, String reason) {

        String dlqTopic = topic + dlqSuffix;

        String dlqPayload =
                "{\"key\":\"" + key + "\",\"value\":\"" + value + "\",\"reason\":\"" + reason + "\"}";

        loggingService.log("WARN", "DLQ",
                "Sending to DLQ: " + reason,
                dlqTopic, null, null, key, dlqPayload.length(), null);

        try {
            CompletableFuture<SendResult<String, String>> future =
                    kafkaTemplate.send(dlqTopic, key, dlqPayload);

            attachCallback(future, dlqTopic, key, dlqPayload, "DLQSend");

        } catch (Exception e) {

            loggingService.log("ERROR", "DLQ",
                    "DLQ send failed: " + e.getMessage(),
                    dlqTopic, null, null, key, value.length(), e.toString());
        }
    }*/

}

