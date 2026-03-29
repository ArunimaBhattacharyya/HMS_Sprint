package com.example.hms.dto;

import lombok.Data;

@Data
public class DepartmentResponse {
    private int departmentId;
    private String name;
    private int head;
    private String headPhysicianName;
}
