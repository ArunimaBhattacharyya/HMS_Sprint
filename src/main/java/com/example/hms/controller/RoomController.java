package com.example.hms.controller;

import com.example.hms.entity.Room;
import com.example.hms.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    //Get all rooms
    @GetMapping("/all")
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    // Create room
    @PostMapping("/create")
    public Room createRoom(@RequestBody Room room) {
        return roomService.saveRoom(room);
    }

    // Get by ID
    @GetMapping("/{id}")
    public Room getRoom(@PathVariable int id) {
        return roomService.getRoomById(id);
    }

    // Update
    @PutMapping("/update/{id}")
    public Room updateRoom(@PathVariable int id, @RequestBody Room room) {
        return roomService.updateRoom(id, room);
    }

    // Delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteRoom(@PathVariable int id) {

        roomService.deleteRoom(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Room deleted successfully");

        return ResponseEntity.ok(response);
    }
}