package com.web.producer.controls;

import com.web.producer.dtos.EmployeeDTO;
import com.web.producer.event.EmployeeProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
public class EmployeeRestController {

    private final EmployeeProducer producer;

    public EmployeeRestController(EmployeeProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public String sendEmployee(@RequestBody EmployeeDTO employee) {
        producer.sendEmployee(employee);
        return "Employee sent to Kafka successfully";
    }

}
