package com.example.hms.controller;

import com.example.hms.dto.StayRequest;
import com.example.hms.dto.StayResponse;
import com.example.hms.service.StayService;

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

public class StayControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StayService stayService;

    @InjectMocks
    private StayController controller;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testCreateStay() throws Exception {
        StayResponse res = new StayResponse();
        res.setStayId(1);

        when(stayService.createStay(any(StayRequest.class))).thenReturn(res);

        mockMvc.perform(post("/stays/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "stayId":1,
                          "patientId":100,
                          "roomId":101,
                          "stayStart":"2025-01-01T10:00:00",
                          "stayEnd":"2025-01-02T10:00:00"
                        }
                        """))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllStay() throws Exception {
        when(stayService.getAllStay()).thenReturn(List.of());

        mockMvc.perform(get("/stays/all"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetStayById() throws Exception {
        StayResponse res = new StayResponse();
        res.setStayId(1);

        when(stayService.getStayById(1)).thenReturn(res);

        mockMvc.perform(get("/stays/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetStayByPatient() throws Exception {
        when(stayService.getStayByPatient(100)).thenReturn(List.of());

        mockMvc.perform(get("/stays/patient/100"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetStayByRoom() throws Exception {
        when(stayService.getStayByRoom(101)).thenReturn(List.of());

        mockMvc.perform(get("/stays/room/101"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateStay() throws Exception {
        StayResponse res = new StayResponse();
        res.setStayId(1);

        when(stayService.updateStay(eq(1), any(StayRequest.class))).thenReturn(res);

        mockMvc.perform(put("/stays/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "stayId":1,
                          "patientId":100,
                          "roomId":101,
                          "stayStart":"2025-01-01T10:00:00",
                          "stayEnd":"2025-01-02T10:00:00"
                        }
                        """))
                .andExpect(status().isOk());
    }

//    @Test
//    public void testDeleteStay() throws Exception {
//        mockMvc.perform(delete("/stays/1"))
//                .andExpect(status().isOk());
//    }
}