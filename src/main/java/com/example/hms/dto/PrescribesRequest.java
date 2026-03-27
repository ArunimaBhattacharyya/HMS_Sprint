package com.example.hms.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PrescribesRequest {
    private int physicianId;
    private int patientSsn;
    private int medicationCode;
    private LocalDateTime date;
    private Integer appointmentId;
    private String dose;
}

