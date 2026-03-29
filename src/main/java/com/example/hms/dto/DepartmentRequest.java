package com.example.hms.dto;

import lombok.Data;

@Data
public class DepartmentRequest {
    private int departmentId;
    private String name;
    private int head;
}
