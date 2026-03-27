package com.example.hms.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PrescribesResponse {
    private int physicianId;
    private String physicianName;

    private int patientSsn;
    private String patientName;

    private int medicationCode;
    private String medicationName;

    private LocalDateTime date;
    private Integer appointmentId;
    private String dose;
}

