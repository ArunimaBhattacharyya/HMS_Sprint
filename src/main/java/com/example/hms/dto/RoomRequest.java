package com.example.hms.dto;

import lombok.Data;

@Data
public class RoomRequest {

    private int roomNumber;
    private String roomType;
    private boolean unavailable;

    private int blockFloor;
    private int blockCode;
}