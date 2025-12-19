package com.web.demo.consumers;

import com.web.demo.dtos.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderKafkaListener {

    private final OrderMessageProcessor processor;

    public OrderKafkaListener(OrderMessageProcessor processor) {
        this.processor = processor;
    }

    @KafkaListener(topics = "${app.kafka.topics.orders}")
    public void listen(
            ConsumerRecord<String, OrderEvent> record,
            Acknowledgment ack) {

        processor.process(record, ack);
    }
}

