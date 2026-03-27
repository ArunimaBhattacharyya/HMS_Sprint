package com.example.hms.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentResponse {
    private int appointmentId;

    private int patientSsn;
    private String patientName;

    private Integer prepNurseId;
    private String prepNurseName;

    private int physicianId;
    private String physicianName;

    private LocalDateTime start;
    private LocalDateTime end;
    private String examinationRoom;
}

