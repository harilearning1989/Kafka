package com.web.demo.consumers;

import com.web.demo.dtos.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductEventConsumer {

    private final ObjectMapper objectMapper;
    //private final OrderAuditService auditService;

    /*@KafkaListener(
            topics = "products-topic",
            containerFactory = "kafkaListenerContainerFactory"
    )*/
    @KafkaListener(
            topics = "products-topic1",
            groupId = "order-audit-group"
    )
    public void consume1(
            ConsumerRecord<String, String> record,
            Acknowledgment ack) {

        try {
            log.info("Consumed message key={} partition={} offset={}",
                    record.key(), record.partition(), record.offset());
            OrderEvent event =
                    objectMapper.readValue(record.value(), OrderEvent.class);
            ack.acknowledge();
        } catch (Exception ex) {
            log.error("Processing failed, will retry / DLT", ex);
            throw ex; // let error handler handle retry + DLT
        }
    }

    @KafkaListener(
            topics = "products-topic",
            groupId = "order-audit-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(
            ConsumerRecord<String, OrderEvent> record,
            Acknowledgment ack) {
        OrderEvent event = record.value();
        log.info("Consumed order {} partition {} offset {}",
                event.getOrderId(), record.partition(), record.offset());
        ack.acknowledge();
    }
    /*public void consume(
            ConsumerRecord<String, String> record,
            Acknowledgment ack) throws Exception {
        OrderEvent event = objectMapper.readValue(record.value(), OrderEvent.class);
        log.info("Consumed order {} partition {} offset {}",
                event.getOrderId(), record.partition(), record.offset());
        ack.acknowledge(); // commit only after
    }*/

    @KafkaListener(
            topics = "products-topic.DLT",
            groupId = "order-audit-dlt-replay"
    )
    public void replay(ConsumerRecord<String, String> record) {
        OrderEvent event = objectMapper.readValue(record.value(), OrderEvent.class);
        log.info("Consumed order {} partition {} offset {}",
                event.getOrderId(), record.partition(), record.offset());
    }

}

