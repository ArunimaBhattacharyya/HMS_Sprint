package com.example.hms.controller;

import com.example.hms.dto.TrainedInDTO;
import com.example.hms.service.TrainedInService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TrainedInControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TrainedInService service;

    @InjectMocks
    private TrainedInController controller;

    private TrainedInDTO dto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        dto = new TrainedInDTO();
        dto.setPhysicianId(1);
        dto.setTreatmentId(101);
        dto.setCertificationDate(LocalDateTime.parse("2025-01-01T10:00:00"));
        dto.setCertificationExpires(LocalDateTime.parse("2026-01-01T10:00:00"));
    }

    // GET ALL
    @Test
    void testGetAll() throws Exception {
        when(service.getAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/trainedin"))
                .andExpect(status().isOk());
    }

    // GET BY ID
    @Test
    void testGetById() throws Exception {
        when(service.getById(1, 101)).thenReturn(dto);

        mockMvc.perform(get("/trainedin/1/101"))
                .andExpect(status().isOk());
    }

    // GET BY PHYSICIAN
    @Test
    void testGetByPhysician() throws Exception {
        when(service.getByPhysician(1)).thenReturn(List.of(dto));

        mockMvc.perform(get("/trainedin/physician/1"))
                .andExpect(status().isOk());
    }

    // GET BY PROCEDURE
    @Test
    void testGetByProcedure() throws Exception {
        when(service.getByProcedure(101)).thenReturn(List.of(dto));

        mockMvc.perform(get("/trainedin/procedure/101"))
                .andExpect(status().isOk());
    }

    // CREATE
    @Test
    void testCreate() throws Exception {
        when(service.save(any(TrainedInDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/trainedin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "physicianId":1,
                                  "treatmentId":101,
                                  "certificationDate":"2025-01-01T10:00:00",
                                  "certificationExpires":"2026-01-01T10:00:00"
                                }
                                """))
                .andExpect(status().isOk());
    }

    // UPDATE
    @Test
    void testUpdate() throws Exception {
        when(service.update(eq(1), eq(101), any(TrainedInDTO.class)))
                .thenReturn(dto);

        mockMvc.perform(put("/trainedin/1/101")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "physicianId":1,
                                  "treatmentId":101,
                                  "certificationDate":"2025-01-01T10:00:00",
                                  "certificationExpires":"2026-01-01T10:00:00"
                                }
                                """))
                .andExpect(status().isOk());
    }

    // DELETE
    @Test
    void testDelete() throws Exception {
        doNothing().when(service).delete(1, 101);

        mockMvc.perform(delete("/trainedin/1/101"))
                .andExpect(status().isOk());
    }

    // EXISTS CHECK
    @Test
    void testExists() throws Exception {
        when(service.exists(1, 101)).thenReturn(true);

        mockMvc.perform(get("/trainedin/check/1/101"))
                .andExpect(status().isOk());
    }
}