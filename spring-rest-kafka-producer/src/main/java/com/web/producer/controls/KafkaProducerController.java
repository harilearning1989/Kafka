package com.web.producer.controls;

import com.web.producer.dtos.Employee;
import com.web.producer.dtos.Student;
import com.web.producer.services.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("produce")
public class KafkaProducerController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @GetMapping
    public String produce(){
        Employee employee = new Employee();
        employee.setId("1");
        employee.setName("Hari");
        employee.setDepartment("MCA");

        Student student = new Student();
        student.setId("11");
        student.setName("Rohan");
        student.setCourse("Nursery");

        kafkaProducerService.produceTopic(employee,student);

        return "success";
    }
}
