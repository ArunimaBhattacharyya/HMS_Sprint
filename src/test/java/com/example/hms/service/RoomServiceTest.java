package com.example.hms.service;

import com.example.hms.dto.RoomRequest;
import com.example.hms.dto.RoomResponse;
import com.example.hms.entity.Block;
import com.example.hms.entity.BlockId;
import com.example.hms.entity.Room;
import com.example.hms.repository.BlockRepository;
import com.example.hms.repository.RoomRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private BlockRepository blockRepository;

    @InjectMocks
    private RoomService roomService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private Block mockBlock(int floor, int code) {
        Block block = new Block();
        block.setBlockFloor(floor);
        block.setBlockCode(code);
        return block;
    }

    private RoomRequest mockRequest() {
        RoomRequest req = new RoomRequest();
        req.setRoomNumber(101);
        req.setRoomType("ICU");
        req.setUnavailable(false);
        req.setBlockFloor(1);
        req.setBlockCode(100);
        return req;
    }

    private Room mockRoom(Block block) {
        Room room = new Room();
        room.setRoomNumber(101);
        room.setRoomType("ICU");
        room.setUnavailable(false);
        room.setBlock(block);
        return room;
    }

    @Test
    public void testCreateRoom() {
        RoomRequest req = mockRequest();
        Block block = mockBlock(1, 100);
        Room room = mockRoom(block);

        when(blockRepository.findById(new BlockId(1, 100))).thenReturn(Optional.of(block));
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        RoomResponse result = roomService.saveRoom(req);

        assertEquals(101, result.getRoomNumber());
        assertEquals("ICU", result.getRoomType());
    }

    @Test
    public void testCreateRoom_BlockNotFound() {
        RoomRequest req = mockRequest();

        when(blockRepository.findById(any(BlockId.class))).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> roomService.saveRoom(req));

        assertEquals("Block not found", ex.getMessage());
    }

    @Test
    public void testGetAllRooms() {
        Block block = mockBlock(1, 100);
        Room room = mockRoom(block);

        when(roomRepository.findAll()).thenReturn(List.of(room));

        List<RoomResponse> result = roomService.getAllRooms();

        assertEquals(1, result.size());
    }

    @Test
    public void testGetRoomById() {
        Block block = mockBlock(1, 100);
        Room room = mockRoom(block);

        when(roomRepository.findById(101)).thenReturn(Optional.of(room));

        RoomResponse result = roomService.getRoomById(101);

        assertEquals(101, result.getRoomNumber());
    }

    @Test
    public void testGetRoomById_NotFound() {
        when(roomRepository.findById(999)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> roomService.getRoomById(999));

        assertEquals("Room not found", ex.getMessage());
    }

    @Test
    public void testUpdateRoom() {
        RoomRequest req = mockRequest();
        Block block = mockBlock(1, 100);
        Room existingRoom = mockRoom(block);

        when(roomRepository.findById(101)).thenReturn(Optional.of(existingRoom));
        when(blockRepository.findById(new BlockId(1, 100))).thenReturn(Optional.of(block));
        when(roomRepository.save(any(Room.class))).thenReturn(existingRoom);

        RoomResponse result = roomService.updateRoom(101, req);

        assertEquals("ICU", result.getRoomType());
    }

    @Test
    public void testUpdateRoom_RoomNotFound() {
        RoomRequest req = mockRequest();

        when(roomRepository.findById(101)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> roomService.updateRoom(101, req));

        assertEquals("Room not found", ex.getMessage());
    }

//    @Test
//    public void testDeleteRoom() {
//        Block block = mockBlock(1, 100);
//        Room room = mockRoom(block);
//
//        when(roomRepository.findById(101)).thenReturn(Optional.of(room));
//
//        roomService.deleteRoom(101);
//
//        verify(roomRepository).delete(room);
//    }

    @Test
    public void testGetRoomsByType() {
        Block block = mockBlock(1, 100);
        Room room = mockRoom(block);

        when(roomRepository.findByRoomType("ICU")).thenReturn(List.of(room));

        List<RoomResponse> result = roomService.getRoomsByType("ICU");

        assertEquals(1, result.size());
    }
}