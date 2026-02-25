package com.web.producer.event;

import com.web.producer.dtos.EmployeeDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmployeeProducer {

    private static final String TOPIC = "employee-topic";

    private final KafkaTemplate<String, EmployeeDTO> kafkaTemplate;

    public EmployeeProducer(KafkaTemplate<String, EmployeeDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEmployee(EmployeeDTO employeeDTO) {
        kafkaTemplate.send(TOPIC, UUID.randomUUID().toString(), employeeDTO);
    }

    public void sendEmployeeCallBack(EmployeeDTO employee) {
        kafkaTemplate.send(TOPIC, UUID.randomUUID().toString(), employee)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.out.println("Sent successfully: " + result.getRecordMetadata());
                    } else {
                        System.out.println("Failed: " + ex.getMessage());
                    }
                });
    }
}
