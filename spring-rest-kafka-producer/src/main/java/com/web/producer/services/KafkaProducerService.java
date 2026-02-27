package com.web.producer.services;

import com.web.producer.dtos.Employee;
import com.web.producer.dtos.Student;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate,
                                ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void produceTopic(Employee employee,Student student) {
        String employeeJson = objectMapper.writeValueAsString(employee);
        kafkaTemplate.send("employee-topic", employeeJson);
        String studentJson = objectMapper.writeValueAsString(student);
        kafkaTemplate.send("student-topic", studentJson);
    }

}
