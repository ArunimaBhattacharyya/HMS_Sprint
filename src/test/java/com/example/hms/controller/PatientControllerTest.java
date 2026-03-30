package com.example.hms.controller;

import com.example.hms.entity.Patient;
import com.example.hms.service.PatientService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PatientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PatientService patientServices;

    @InjectMocks
    private PatientController controller;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetAllPatients() throws Exception {
        when(patientServices.getAllPatients()).thenReturn(List.of());

        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPatientById() throws Exception {
        Patient patient = buildPatient(100000001, "John Smith", "Street A", "123456", 1000000001L, 1);
        when(patientServices.getPatientById(100000001)).thenReturn(Optional.of(patient));

        mockMvc.perform(get("/patients/100000001"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPatientByName() throws Exception {
        Patient patient = buildPatient(100000001, "John Smith", "Street A", "123456", 1000000001L, 1);
        when(patientServices.getPatientByName("John Smith")).thenReturn(List.of(patient));

        mockMvc.perform(get("/patients/name/John Smith"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPatientByPcp() throws Exception {
        Patient patient = buildPatient(100000001, "John Smith", "Street A", "123456", 1000000001L, 1);
        when(patientServices.getPatientByPcp(1)).thenReturn(List.of(patient));

        mockMvc.perform(get("/patients/pcp/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreatePatient() throws Exception {
        Patient patient = buildPatient(100000006, "Ayan Again 2", "Street F", "999999", 1000000010L, 2);
        when(patientServices.createPatient(any(Patient.class))).thenReturn(patient);

        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"ssn\":100000006,\"name\":\"Ayan Again 2\",\"address\":\"Street F\",\"phone\":\"999999\",\"insuranceId\":1000000010,\"pcp\":2}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdatePatient() throws Exception {
        Patient updated = buildPatient(100000001, "John Updated", "New Address", "987654", 1000000099L, 9);
        when(patientServices.updatePatient(eq(100000001), any(Patient.class))).thenReturn(updated);

        mockMvc.perform(put("/patients/100000001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"ssn\":100000001,\"name\":\"John Updated\",\"address\":\"New Address\",\"phone\":\"987654\",\"insuranceId\":1000000099,\"pcp\":9}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeletePatient() throws Exception {
        mockMvc.perform(delete("/patients/100000001"))
                .andExpect(status().isOk());
    }

    private Patient buildPatient(int ssn, String name, String address, String phone, long insuranceId, Integer pcp) {
        Patient patient = new Patient();
        patient.setSsn(ssn);
        patient.setName(name);
        patient.setAddress(address);
        patient.setPhone(phone);
        patient.setInsuranceId(insuranceId);
        patient.setPcp(pcp);
        return patient;
    }
}

