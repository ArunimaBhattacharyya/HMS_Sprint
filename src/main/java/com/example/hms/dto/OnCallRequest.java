package com.example.hms.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OnCallRequest {
    private int nurseId;
    private int blockFloor;
    private int blockCode;
    private LocalDateTime onCallStart;
    private LocalDateTime onCallEnd;
}
