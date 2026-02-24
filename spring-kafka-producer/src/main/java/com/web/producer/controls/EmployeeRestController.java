package com.web.producer.controls;

import com.web.producer.dtos.Employee;
import com.web.producer.event.EmployeeProducer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeRestController {

    private final EmployeeProducer producer;

    public EmployeeRestController(EmployeeProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public String sendEmployee(@RequestBody Employee employee) {
        producer.sendEmployee(employee);
        return "Employee sent to Kafka successfully";
    }

}
