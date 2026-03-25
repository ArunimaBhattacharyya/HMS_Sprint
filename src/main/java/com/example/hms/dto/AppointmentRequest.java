package com.example.hms.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequest {
    private int appointmentId;
    private long patientSsn;
    private Integer prepNurseId;
    private int physicianId;
    private LocalDateTime start;
    private LocalDateTime end;
    private String examinationRoom;
}

