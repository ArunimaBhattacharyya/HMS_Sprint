package com.example.hms.controller;

import com.example.hms.dto.PrescribesRequest;
import com.example.hms.dto.PrescribesResponse;
import com.example.hms.service.PrescribesService;
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

public class PrescribesControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PrescribesService prescribesService;

    @InjectMocks
    private PrescribesController controller;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testCreate() throws Exception {
        when(prescribesService.create(any(PrescribesRequest.class))).thenReturn(buildResponse(1, 100000001, 1));

        mockMvc.perform(post("/prescribes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"physicianId\":1,\"patientSsn\":100000001,\"medicationCode\":1,\"date\":\"2008-04-24T10:47:00\",\"appointmentId\":13216584,\"dose\":\"5\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAll() throws Exception {
        when(prescribesService.getAll()).thenReturn(List.of(buildResponse(1, 100000001, 1)));

        mockMvc.perform(get("/prescribes"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetById() throws Exception {
        when(prescribesService.getById(eq(1), eq(100000001), eq(1), any(LocalDateTime.class)))
                .thenReturn(buildResponse(1, 100000001, 1));

        mockMvc.perform(get("/prescribes/1/100000001/1/2008-04-24T10:47:00"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetByPhysician() throws Exception {
        when(prescribesService.getByPhysician(1)).thenReturn(List.of(buildResponse(1, 100000001, 1)));

        mockMvc.perform(get("/prescribes/physician/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetByPatient() throws Exception {
        when(prescribesService.getByPatient(100000001)).thenReturn(List.of(buildResponse(1, 100000001, 1)));

        mockMvc.perform(get("/prescribes/patient/100000001"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetByMedication() throws Exception {
        when(prescribesService.getByMedication(1)).thenReturn(List.of(buildResponse(1, 100000001, 1)));

        mockMvc.perform(get("/prescribes/medication/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testSearchByPhysicianPatientMedication() throws Exception {
        when(prescribesService.getByPhysicianPatientMedication(1, 100000001, 1))
                .thenReturn(List.of(buildResponse(1, 100000001, 1)));

        mockMvc.perform(get("/prescribes/search")
                        .param("physicianId", "1")
                        .param("patientSsn", "100000001")
                        .param("medicationCode", "1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetByAppointment() throws Exception {
        when(prescribesService.getByAppointment(13216584)).thenReturn(List.of(buildResponse(1, 100000001, 1)));

        mockMvc.perform(get("/prescribes/appointment/13216584"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdate() throws Exception {
        when(prescribesService.update(eq(1), eq(100000001), eq(1), any(LocalDateTime.class), any(PrescribesRequest.class)))
                .thenReturn(buildResponse(1, 100000001, 10));

        mockMvc.perform(put("/prescribes/1/100000001/1/2008-04-24T10:47:00")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"medicationCode\":10,\"appointmentId\":13216584,\"dose\":\"8\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/prescribes/1/100000001/1/2008-04-24T10:47:00"))
                .andExpect(status().isOk());
    }

    private PrescribesResponse buildResponse(int physicianId, int patientSsn, int medicationCode) {
        PrescribesResponse response = new PrescribesResponse();
        response.setPhysicianId(physicianId);
        response.setPhysicianName("John Dorian");
        response.setPatientSsn(patientSsn);
        response.setPatientName("John Smith");
        response.setMedicationCode(medicationCode);
        response.setMedicationName(medicationCode == 10 ? "Paracetamol" : "Procrastin-X");
        response.setDate(LocalDateTime.of(2008, 4, 24, 10, 47));
        response.setAppointmentId(13216584);
        response.setDose("8");
        return response;
    }
}

