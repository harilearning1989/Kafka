package com.web.demo.controls;

import java.util.Map;

import com.web.demo.services.KafkaProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final KafkaProducerService producer;

    public CompanyController(KafkaProducerService producer) {
        this.producer = producer;
    }

    @PostMapping
    public ResponseEntity<?> createCompany(@RequestBody Map<String, Object> payload) {
        producer.send("companies-topic", payload.getOrDefault("companyId", "").toString(), payload);
        return ResponseEntity.accepted().body("Company message queued");
    }
}
