package com.web.demo.controls;

import com.web.demo.services.KafkaProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final KafkaProducerService producer;

    public ProductController(KafkaProducerService producer) {
        this.producer = producer;
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Map<String, Object> payload) {
        producer.send("products-topic", payload.getOrDefault("id", "").toString(), payload);
        return ResponseEntity.accepted().body("Product message queued");
    }
}
