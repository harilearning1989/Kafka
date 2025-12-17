package com.web.demo.controls;

import com.web.demo.services.KafkaProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/products")
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

    @GetMapping("/test")
    public String test() {
        return "Server is running!";
    }
}
