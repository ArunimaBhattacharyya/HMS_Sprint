package com.example.hms.controller;

import com.example.hms.dto.AppointmentResponse;
import com.example.hms.service.AppointmentService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AppointmentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AppointmentService appointmentService;

    @InjectMocks
    private AppointmentController controller;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testCreateAppointment() throws Exception {
        when(appointmentService.create(any())).thenReturn(buildResponse(13216584));

        mockMvc.perform(post("/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"appointmentId\":13216584,\"patientSsn\":100000001,\"prepNurseId\":101,\"physicianId\":1,\"start\":\"2008-04-24T10:47:00\",\"end\":\"2008-04-24T11:47:00\",\"examinationRoom\":\"A\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllAppointments() throws Exception {
        when(appointmentService.getAll()).thenReturn(List.of(buildResponse(13216584), buildResponse(86213939)));

        mockMvc.perform(get("/appointments"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAppointmentById() throws Exception {
        when(appointmentService.getById(13216584)).thenReturn(buildResponse(13216584));

        mockMvc.perform(get("/appointments/13216584"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAppointmentsByPatient() throws Exception {
        when(appointmentService.getByPatient(100000001)).thenReturn(List.of(buildResponse(13216584)));

        mockMvc.perform(get("/appointments/patient/100000001"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAppointmentsByNurse() throws Exception {
        when(appointmentService.getByNurse(101)).thenReturn(List.of(buildResponse(13216584)));

        mockMvc.perform(get("/appointments/nurse/101"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAppointmentsByPhysician() throws Exception {
        when(appointmentService.getByPhysician(1)).thenReturn(List.of(buildResponse(13216584)));

        mockMvc.perform(get("/appointments/physician/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateAppointment() throws Exception {
        when(appointmentService.update(eq(13216584), any())).thenReturn(buildResponse(13216584));

        mockMvc.perform(put("/appointments/13216584")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"appointmentId\":13216584,\"patientSsn\":100000001,\"prepNurseId\":101,\"physicianId\":1,\"start\":\"2008-04-24T10:47:00\",\"end\":\"2008-04-24T11:47:00\",\"examinationRoom\":\"A\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteAppointment() throws Exception {
        mockMvc.perform(delete("/appointments/13216584"))
                .andExpect(status().isOk());
    }

    private AppointmentResponse buildResponse(int id) {
        AppointmentResponse response = new AppointmentResponse();
        response.setAppointmentId(id);
        response.setPatientSsn(100000001);
        response.setPatientName("John Smith");
        response.setPrepNurseId(101);
        response.setPrepNurseName("Nina");
        response.setPhysicianId(1);
        response.setPhysicianName("John Dorian");
        response.setStart(LocalDateTime.of(2008, 4, 24, 10, 47));
        response.setEnd(LocalDateTime.of(2008, 4, 24, 11, 47));
        response.setExaminationRoom("A");
        return response;
    }
}

