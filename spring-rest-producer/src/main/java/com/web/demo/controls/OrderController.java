package com.web.demo.controls;

import com.web.demo.services.KafkaProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final KafkaProducerService producer;

    public OrderController(KafkaProducerService producer) {
        this.producer = producer;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> payload) {
        producer.send("orders-topic", payload.getOrDefault("orderId", "").toString(), payload);
        return ResponseEntity.accepted().body("Order message queued");
    }
}
