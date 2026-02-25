package com.web.consumer.dtos;

public record EmployeeDTO(
        String id,
        String name,
        String department,
        double salary
) {
}
