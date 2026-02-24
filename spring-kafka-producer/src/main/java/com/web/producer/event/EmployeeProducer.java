package com.web.producer.event;

import com.web.producer.dtos.Employee;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmployeeProducer {

    private static final String TOPIC = "employee-topic";

    private final KafkaTemplate<String, Employee> kafkaTemplate;

    public EmployeeProducer(KafkaTemplate<String, Employee> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEmployee(Employee employee) {
        kafkaTemplate.send(TOPIC, employee.getId(), employee);
    }

    public void sendEmployeeCallBack(Employee employee) {
        kafkaTemplate.send(TOPIC, employee.getId(), employee)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.out.println("Sent successfully: " + result.getRecordMetadata());
                    } else {
                        System.out.println("Failed: " + ex.getMessage());
                    }
                });
    }
}
