package com.example.hms.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OnCallResponse {

    private int nurseId;
    private String nurseName;

    private int blockFloor;
    private int blockCode;

    private LocalDateTime onCallStart;
    private LocalDateTime onCallEnd;
}