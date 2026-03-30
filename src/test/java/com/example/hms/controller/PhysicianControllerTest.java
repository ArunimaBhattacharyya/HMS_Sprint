package com.example.hms.controller;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.hms.entity.Physician;
import com.example.hms.service.PhysicianService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

public class PhysicianControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PhysicianService physicianService;

    @InjectMocks
    private PhysicianController controller;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetAllPhysicians() throws Exception {
        when(physicianService.getAllPhysicians()).thenReturn(List.of());
        mockMvc.perform(get("/physicians"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPhysicianById() throws Exception {
        Physician physician = new Physician(1, "John Dorian", "Staff Internist", 111111111);
        when(physicianService.getPhysicianById(1)).thenReturn(physician);
        mockMvc.perform(get("/physicians/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPhysicianByName() throws Exception {
        Physician physician = new Physician(1, "John Dorian", "Staff Internist", 111111111);
        when(physicianService.getPhysicianByName("John Dorian")).thenReturn(List.of(physician));
        mockMvc.perform(get("/physicians/name/John Dorian"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPhysicianByPosition() throws Exception {
        Physician physician = new Physician(1, "John Dorian", "Staff Internist", 111111111);
        when(physicianService.getPhysicianByPosition("Staff Internist")).thenReturn(List.of(physician));
        mockMvc.perform(get("/physicians/position/Staff Internist"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddPhysician() throws Exception {
        Physician physician = new Physician(1, "John Dorian", "Staff Internist", 111111111);
        when(physicianService.addPhysician(any(Physician.class))).thenReturn(physician);
        mockMvc.perform(post("/physicians")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"employeeId\":1,\"name\":\"John Dorian\",\"position\":\"Staff Internist\",\"ssn\":111111111}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdatePhysician() throws Exception {
        Physician updatedPhysician = new Physician(1, "John Dorian", "Senior Attending", 111111111);
        when(physicianService.updatePhysician(eq(1), any(Physician.class))).thenReturn(updatedPhysician);
        mockMvc.perform(put("/physicians/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"employeeId\":1,\"name\":\"John Dorian\",\"position\":\"Senior Attending\",\"ssn\":111111111}"))
                .andExpect(status().isOk());
    }
}
