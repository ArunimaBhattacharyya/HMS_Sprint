package com.example.hms.controller;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.hms.entity.Nurse;
import com.example.hms.service.NurseService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

public class NurseControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NurseService nurseServices;

    @InjectMocks
    private NurseController controller;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetAllNurses() throws Exception {
        when(nurseServices.getAllNurses()).thenReturn(List.of());
        mockMvc.perform(get("/nurses"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetNurseById() throws Exception {
        Nurse nurse = new Nurse(1, "Arunima", "Head Nurse", 1, 100000001);
        when(nurseServices.getNurseById(1)).thenReturn(Optional.of(nurse));
        mockMvc.perform(get("/nurses/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetNurseByName() throws Exception {
        Nurse nurse = new Nurse(1, "Arunima", "Head Nurse", 1, 100000001);
        when(nurseServices.getNurseByName("Arunima")).thenReturn(List.of(nurse));
        mockMvc.perform(get("/nurses/name/Arunima"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetNurseByPosition() throws Exception {
        Nurse nurse = new Nurse(1, "Arunima", "Head Nurse", 1, 100000001);
        when(nurseServices.getNurseByPosition("Head Nurse")).thenReturn(List.of(nurse));
        mockMvc.perform(get("/nurses/position/Head Nurse"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateNurse() throws Exception {
        Nurse nurse = new Nurse(1, "Arunima", "Head Nurse", 1, 100000001);
        when(nurseServices.createNurse(any(Nurse.class))).thenReturn(nurse);
        mockMvc.perform(post("/nurses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"employeeId\":1,\"name\":\"Alice\",\"position\":\"Head Nurse\",\"registered\":1,\"ssn\":100000001}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateNurse() throws Exception {
        Nurse updatedNurse = new Nurse(1, "Arunima Bhattacharyya", "Senior Nurse", 1, 100000002);
        when(nurseServices.updateNurse(eq(1), any(Nurse.class))).thenReturn(updatedNurse);
        mockMvc.perform(put("/nurses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"employeeId\":1,\"name\":\"Arunima Bhattacharyya\",\"position\":\"Senior Nurse\",\"registered\":1,\"ssn\":100000002}"))
                .andExpect(status().isOk());
    }
}