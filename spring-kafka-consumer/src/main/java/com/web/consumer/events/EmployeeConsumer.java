package com.web.consumer.events;

import com.web.consumer.dtos.Employee;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmployeeConsumer {

    @KafkaListener(
            topics = "employee-topic",
            groupId = "employee-group"
    )
    public void consume(Employee employee) {

        System.out.println("Received Employee:");
        System.out.println("ID: " + employee.getId());
        System.out.println("Name: " + employee.getName());
        System.out.println("Department: " + employee.getDepartment());
        System.out.println("Salary: " + employee.getSalary());
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
