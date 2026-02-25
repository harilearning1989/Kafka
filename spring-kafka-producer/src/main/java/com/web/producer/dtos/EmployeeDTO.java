package com.web.producer.dtos;

public record EmployeeDTO(
        String id,
        String name,
        String department,
        double salary
) {
}
