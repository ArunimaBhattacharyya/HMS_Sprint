package com.example.hms.service;

import com.example.hms.dto.RoomRequest;
import com.example.hms.dto.RoomResponse;
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

    private Room convertToEntity(RoomRequest req) {

        Room room = new Room();
        room.setRoomNumber(req.getRoomNumber());
        room.setRoomType(req.getRoomType());
        room.setUnavailable(req.isUnavailable());

        BlockId blockId = new BlockId(req.getBlockFloor(), req.getBlockCode());

        Block block = blockRepository.findById(blockId)
                .orElseThrow(() -> new RuntimeException("Block not found"));

        room.setBlock(block);

        return room;
    }

    private RoomResponse convertToResponse(Room room) {

        RoomResponse res = new RoomResponse();

        res.setRoomNumber(room.getRoomNumber());
        res.setRoomType(room.getRoomType());
        res.setUnavailable(room.isUnavailable());

        res.setBlockFloor(room.getBlock().getBlockFloor());
        res.setBlockCode(room.getBlock().getBlockCode());

        return res;
    }

    public RoomResponse saveRoom(RoomRequest req) {

        Room room = convertToEntity(req);
        Room saved = roomRepository.save(room);

        return convertToResponse(saved);
    }


    public List<RoomResponse> getAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public RoomResponse getRoomById(int roomNumber) {

        Room room = roomRepository.findById(roomNumber)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        return convertToResponse(room);
    }

    public RoomResponse updateRoom(int roomNumber, RoomRequest req) {

        Room room = roomRepository.findById(roomNumber)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        room.setRoomType(req.getRoomType());
        room.setUnavailable(req.isUnavailable());

        // Update Block
        BlockId blockId = new BlockId(req.getBlockFloor(), req.getBlockCode());

        Block block = blockRepository.findById(blockId)
                .orElseThrow(() -> new RuntimeException("Block not found"));

        room.setBlock(block);

        Room updated = roomRepository.save(room);

        return convertToResponse(updated);
    }


    public void deleteRoom(int roomNumber) {

        Room room = roomRepository.findById(roomNumber)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        roomRepository.delete(room);
    }

    public List<RoomResponse> getRoomsByType(String roomType) {
        return roomRepository.findByRoomType(roomType)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

}