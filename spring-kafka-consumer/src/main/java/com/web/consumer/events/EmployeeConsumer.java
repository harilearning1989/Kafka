package com.web.consumer.events;

import com.web.consumer.dtos.EmployeeDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmployeeConsumer {

    @KafkaListener(
            topics = "employee-topic",
            groupId = "employee-group"
    )
    public void consume(EmployeeDTO employee) {

        System.out.println("Received Employee:");
        System.out.println("ID: " + employee.id());
        System.out.println("Name: " + employee.name());
        System.out.println("Department: " + employee.department());
        System.out.println("Salary: " + employee.salary());
        System.out.println("----------------------------------");
    }

   /* kafka1:
    bootstrap-servers: localhost:9092
    consumer:
    group-id: employee-group
    auto-offset-reset: earliest
    key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
    value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
    properties:
    spring.json.trusted.packages: "*"*/
}
