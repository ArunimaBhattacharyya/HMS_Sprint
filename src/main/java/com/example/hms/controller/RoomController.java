package com.example.hms.controller;

import com.example.hms.dto.RoomRequest;
import com.example.hms.dto.RoomResponse;
import com.example.hms.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    // Get all rooms
    @GetMapping("/all")
    public List<RoomResponse> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/type/{roomType}")
    public List<RoomResponse> getRoomsByType(@PathVariable String roomType) {
        return roomService.getRoomsByType(roomType);
    }


    // Get by ID
    @GetMapping("/{id}")
    public RoomResponse getRoom(@PathVariable int id) {
        return roomService.getRoomById(id);
    }

    // Create room
    @PostMapping("/create")
    public RoomResponse createRoom(@RequestBody RoomRequest request) {
        return roomService.saveRoom(request);
    }

    // Update room
    @PutMapping("/update/{id}")
    public RoomResponse updateRoom(@PathVariable int id,
                                   @RequestBody RoomRequest request) {
        return roomService.updateRoom(id, request);
    }

    // Delete room
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteRoom(@PathVariable int id) {

        roomService.deleteRoom(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Room deleted successfully");

        return ResponseEntity.ok(response);
    }
}