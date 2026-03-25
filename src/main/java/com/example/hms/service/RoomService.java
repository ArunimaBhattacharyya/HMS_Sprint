package com.example.hms.service;

import com.example.hms.entity.Block;
import com.example.hms.entity.BlockId;
import com.example.hms.entity.Room;
import com.example.hms.repository.BlockRepository;
import com.example.hms.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BlockRepository blockRepository;

    // Create Room
    public Room saveRoom(Room room) {

        // Validate Block exists
        BlockId blockId = new BlockId(
                room.getBlock().getBlockFloor(),
                room.getBlock().getBlockCode()
        );

        Block block = blockRepository.findById(blockId)
                .orElseThrow(() -> new RuntimeException("Block not found"));

        room.setBlock(block);

        return roomRepository.save(room);
    }

    // Get all Rooms
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    // Get Room by ID
    public Room getRoomById(int roomNumber) {
        return roomRepository.findById(roomNumber)
                .orElseThrow(() -> new RuntimeException("Room not found"));
    }

    // Update Room
    public Room updateRoom(int roomNumber, Room roomDetails) {

        Room room = roomRepository.findById(roomNumber)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        room.setRoomType(roomDetails.getRoomType());
        room.setUnavailable(roomDetails.isUnavailable());

        // Update Block if needed
        if (roomDetails.getBlock() != null) {

            BlockId blockId = new BlockId(
                    roomDetails.getBlock().getBlockFloor(),
                    roomDetails.getBlock().getBlockCode()
            );

            Block block = blockRepository.findById(blockId)
                    .orElseThrow(() -> new RuntimeException("Block not found"));

            room.setBlock(block);
        }

        return roomRepository.save(room);
    }

    // Delete Room
    public void deleteRoom(int roomNumber) {

        Room room = roomRepository.findById(roomNumber)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        roomRepository.delete(room);
    }
}