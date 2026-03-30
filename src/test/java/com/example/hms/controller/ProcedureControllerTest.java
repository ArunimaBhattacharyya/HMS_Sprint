package com.example.hms.controller;

import com.example.hms.entity.Procedure;
import com.example.hms.service.ProcedureService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProcedureControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProcedureService service;

    @InjectMocks
    private ProcedureController controller;

    private Procedure procedure;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        procedure = new Procedure(101, "Heart Surgery", 50000.0);
    }

    // GET ALL
    @Test
    void testGetAll() throws Exception {
        when(service.getAllProcedures()).thenReturn(List.of(procedure));

        mockMvc.perform(get("/procedures"))
                .andExpect(status().isOk());
    }

    // GET BY ID
    @Test
    void testGetById() throws Exception {
        when(service.getProcedureById(101)).thenReturn(procedure);

        mockMvc.perform(get("/procedures/101"))
                .andExpect(status().isOk());
    }

    // CREATE
    @Test
    void testCreate() throws Exception {
        when(service.saveProcedure(any(Procedure.class))).thenReturn(procedure);

        mockMvc.perform(post("/procedures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "code":101,
                                  "name":"Heart Surgery",
                                  "cost":50000
                                }
                                """))
                .andExpect(status().isOk());
    }

    // UPDATE
    @Test
    void testUpdate() throws Exception {
        when(service.updateProcedure(eq(101), any(Procedure.class)))
                .thenReturn(procedure);

        mockMvc.perform(put("/procedures/101")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "code":101,
                                  "name":"Heart Surgery",
                                  "cost":60000
                                }
                                """))
                .andExpect(status().isOk());
    }

    // DELETE
    @Test
    void testDelete() throws Exception {
        doNothing().when(service).deleteProcedure(101);

        mockMvc.perform(delete("/procedures/101"))
                .andExpect(status().isOk());
    }

    // SEARCH BY NAME
    @Test
    void testSearchByName() throws Exception {
        when(service.getByName("Heart")).thenReturn(List.of(procedure));

        mockMvc.perform(get("/procedures/search")
                        .param("name", "Heart"))
                .andExpect(status().isOk());
    }

    // FILTER BY COST
    @Test
    void testFilterByCost() throws Exception {
        when(service.getByCostRange(10000, 60000))
                .thenReturn(List.of(procedure));

        mockMvc.perform(get("/procedures/filterByCost")
                        .param("min", "10000")
                        .param("max", "60000"))
                .andExpect(status().isOk());
    }
}