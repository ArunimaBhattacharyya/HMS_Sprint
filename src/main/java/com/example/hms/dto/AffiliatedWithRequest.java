package com.example.hms.dto;

import lombok.Data;

@Data
public class AffiliatedWithRequest {
    private int physician;
    private int department;
    private Boolean primaryAffiliation;
}