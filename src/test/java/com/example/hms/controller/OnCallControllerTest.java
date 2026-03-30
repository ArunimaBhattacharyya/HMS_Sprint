package com.example.hms.controller;

import com.example.hms.dto.OnCallResponse;
import com.example.hms.service.OnCallService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OnCallControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OnCallService onCallService;

    @InjectMocks
    private OnCallController onCallController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(onCallController).build();
    }

    @Test
    public void testCreate() throws Exception {
        OnCallResponse response = new OnCallResponse();
        response.setNurseId(1);
        response.setNurseName("Arunima");
        response.setBlockFloor(1);
        response.setBlockCode(101);

        when(onCallService.create(any())).thenReturn(response);

        mockMvc.perform(post("/oncall")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nurseId\":1,\"blockFloor\":1,\"blockCode\":101,\"onCallStart\":\"2024-01-01T08:00\",\"onCallEnd\":\"2024-01-01T16:00\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAll() throws Exception {
        when(onCallService.getAll()).thenReturn(List.of());
        mockMvc.perform(get("/oncall"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetByNurse() throws Exception {
        OnCallResponse response = new OnCallResponse();
        response.setNurseId(1);
        response.setNurseName("Arunima");

        when(onCallService.getByNurse(1)).thenReturn(List.of(response));
        mockMvc.perform(get("/oncall/nurse/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetByFloor() throws Exception {
        OnCallResponse response = new OnCallResponse();
        response.setBlockFloor(1);

        when(onCallService.getByFloor(1)).thenReturn(List.of(response));
        mockMvc.perform(get("/oncall/floor/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetByCode() throws Exception {
        OnCallResponse response = new OnCallResponse();
        response.setBlockCode(101);

        when(onCallService.getByFloor(101)).thenReturn(List.of(response));
        mockMvc.perform(get("/oncall/code/101"))
                .andExpect(status().isOk());
    }


}
