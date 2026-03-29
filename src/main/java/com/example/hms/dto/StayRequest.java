package com.example.hms.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StayRequest {
    private int stayId;
    private int patientId;   // SSN
    private int roomId;      // RoomNumber

    private LocalDateTime stayStart;
    private LocalDateTime stayEnd;
}
