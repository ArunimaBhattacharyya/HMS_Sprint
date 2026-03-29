package com.example.hms.dto;

import lombok.Data;

@Data
public class AffiliatedWithResponse {
    private int physician;
    private String physicianName;
    private int department;
    private String departmentName;
    private Boolean primaryAffiliation;
}
