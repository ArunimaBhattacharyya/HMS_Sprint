package com.example.hms.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StayResponse {

    private int stayId;

    private int patientId;
    private String patientName;

    private int roomId;
    private String roomType;

    private LocalDateTime stayStart;
    private LocalDateTime stayEnd;
}