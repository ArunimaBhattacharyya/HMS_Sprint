package com.example.hms.controller;

import com.example.hms.service.UndergoesService;
import com.example.hms.dto.UndergoesDTO;
import com.example.hms.entity.UndergoesId;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UndergoesControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UndergoesService service;

    @InjectMocks
    private UndergoesController controller;

    private UndergoesDTO dto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        dto = new UndergoesDTO();
        dto.setPatient(1);
        dto.setProcedure(101);
        dto.setStay(10);
        dto.setPhysician(5);
        dto.setAssistingNurse(2);
        dto.setDateUndergoes(LocalDateTime.parse("2025-03-01T10:00:00"));
    }

    // GET ALL
    @Test
    public void testGetAll() throws Exception {
        when(service.getAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/undergoes"))
                .andExpect(status().isOk());
    }

    // GET BY ID (Composite Key)
    @Test
    public void testGetById() throws Exception {
        when(service.getById(eq(1), eq(101), eq(10),
                eq(LocalDateTime.parse("2025-03-01T10:00:00"))))
                .thenReturn(dto);

        mockMvc.perform(get("/undergoes/1/101/10/2025-03-01T10:00:00"))
                .andExpect(status().isOk());
    }

    // FILTER - Patient
    @Test
    public void testGetByPatient() throws Exception {
        when(service.getByPatient(1)).thenReturn(List.of(dto));

        mockMvc.perform(get("/undergoes/patient/1"))
                .andExpect(status().isOk());
    }

    // FILTER - Procedure
    @Test
    public void testGetByProcedure() throws Exception {
        when(service.getByProcedure(101)).thenReturn(List.of(dto));

        mockMvc.perform(get("/undergoes/procedure/101"))
                .andExpect(status().isOk());
    }

    // FILTER - Stay
    @Test
    public void testGetByStay() throws Exception {
        when(service.getByStay(10)).thenReturn(List.of(dto));

        mockMvc.perform(get("/undergoes/stay/10"))
                .andExpect(status().isOk());
    }

    // FILTER - Physician
    @Test
    public void testGetByPhysician() throws Exception {
        when(service.getByPhysician(5)).thenReturn(List.of(dto));

        mockMvc.perform(get("/undergoes/physician/5"))
                .andExpect(status().isOk());
    }

    // FILTER - Nurse
    @Test
    public void testGetByNurse() throws Exception {
        when(service.getByNurse(2)).thenReturn(List.of(dto));

        mockMvc.perform(get("/undergoes/nurse/2"))
                .andExpect(status().isOk());
    }

    // FILTER - Date Range
    @Test
    public void testGetByDateRange() throws Exception {
        when(service.getByDateRange(
                LocalDateTime.parse("2025-03-01T00:00:00"),
                LocalDateTime.parse("2025-03-31T23:59:59")))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/undergoes/date-range")
                        .param("start", "2025-03-01T00:00:00")
                        .param("end", "2025-03-31T23:59:59"))
                .andExpect(status().isOk());
    }

    // CREATE
    @Test
    public void testCreate() throws Exception {
        when(service.create(any(UndergoesDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/undergoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "patient":1,
                                  "procedure":101,
                                  "stay":10,
                                  "physician":5,
                                  "assistingNurse":2,
                                  "dateUndergoes":"2025-03-01T10:00:00"
                                }
                                """))
                .andExpect(status().isOk());
    }

    // UPDATE
    @Test
    public void testUpdate() throws Exception {
        when(service.update(
                eq(1), eq(101), eq(10),
                eq(LocalDateTime.parse("2025-03-01T10:00:00")),
                any(UndergoesDTO.class)))
                .thenReturn(dto);

        mockMvc.perform(put("/undergoes/1/101/10/2025-03-01T10:00:00")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "patient":1,
                                  "procedure":101,
                                  "stay":10,
                                  "physician":5,
                                  "assistingNurse":2,
                                  "dateUndergoes":"2025-03-01T10:00:00"
                                }
                                """))
                .andExpect(status().isOk());
    }

    // DELETE
    @Test
    public void testDelete() throws Exception {

        doNothing().when(service).delete(any(UndergoesId.class));

        mockMvc.perform(delete("/undergoes/1/101/10/2025-03-01T10:00:00"))
                .andExpect(status().isOk());
    }
}