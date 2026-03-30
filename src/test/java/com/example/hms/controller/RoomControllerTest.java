package com.example.hms.controller;

import com.example.hms.dto.RoomRequest;
import com.example.hms.dto.RoomResponse;
import com.example.hms.service.RoomService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RoomControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RoomService roomService;

    @InjectMocks
    private RoomController controller;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetAllRooms() throws Exception {
        when(roomService.getAllRooms()).thenReturn(List.of());

        mockMvc.perform(get("/room/all"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetRoomById() throws Exception {
        RoomResponse res = new RoomResponse();
        res.setRoomNumber(101);

        when(roomService.getRoomById(101)).thenReturn(res);

        mockMvc.perform(get("/room/101"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetRoomsByType() throws Exception {
        when(roomService.getRoomsByType("ICU")).thenReturn(List.of());

        mockMvc.perform(get("/room/type/ICU"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateRoom() throws Exception {
        RoomResponse res = new RoomResponse();
        res.setRoomNumber(101);

        when(roomService.saveRoom(any(RoomRequest.class))).thenReturn(res);

        mockMvc.perform(post("/room/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "roomNumber":101,
                          "roomType":"ICU",
                          "unavailable":false,
                          "blockFloor":1,
                          "blockCode":100
                        }
                        """))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateRoom() throws Exception {
        RoomResponse res = new RoomResponse();
        res.setRoomNumber(101);

        when(roomService.updateRoom(eq(101), any(RoomRequest.class)))
                .thenReturn(res);

        mockMvc.perform(put("/room/update/101")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "roomNumber":101,
                          "roomType":"ICU",
                          "unavailable":false,
                          "blockFloor":1,
                          "blockCode":100
                        }
                        """))
                .andExpect(status().isOk());
    }

//    @Test
//    public void testDeleteRoom() throws Exception {
//        mockMvc.perform(delete("/room/delete/101"))
//                .andExpect(status().isOk());
//    }
}