package com.web.consumer.listener;

import com.web.consumer.dtos.Employee;
import com.web.consumer.dtos.Student;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class KafkaConsumerService {

    private final ObjectMapper objectMapper;

    public KafkaConsumerService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "employee-topic")
    public void consumeEmployee(String message) throws Exception {

        Employee employee = objectMapper.readValue(message, Employee.class);

        System.out.println("Received Employee: " + employee.getName());
    }

    @KafkaListener(topics = "student-topic")
    public void consumeStudent(String message) throws Exception {

        Student student = objectMapper.readValue(message, Student.class);

        System.out.println("Received Student: " + student.getName());
    }
}
