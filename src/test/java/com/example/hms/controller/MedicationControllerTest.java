package com.example.hms.controller;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.hms.entity.Medication;
import com.example.hms.service.MedicationService;

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

public class MedicationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MedicationService medicationService;

    @InjectMocks
    private MedicationController controller;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetAllMedication() throws Exception {
        when(medicationService.getAllMedication()).thenReturn(List.of());
        mockMvc.perform(get("/medications"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetMedicationByCode() throws Exception {
        Medication medication = new Medication(1, "Paracetamol", "Calpol", "Pain reliever");
        when(medicationService.getMedicaltionByCode(1)).thenReturn(Optional.of(medication));
        mockMvc.perform(get("/medications/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetMedicationByName() throws Exception {
        Medication medication = new Medication(1, "Paracetamol", "Calpol", "Pain reliever");
        when(medicationService.getMedicationByName("Paracetamol")).thenReturn(List.of(medication));
        mockMvc.perform(get("/medications/name/Paracetamol"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetMedicationByBrand() throws Exception {
        Medication medication = new Medication(1, "Paracetamol", "Calpol", "Pain reliever");
        when(medicationService.getMedicationByBrand("Calpol")).thenReturn(List.of(medication));
        mockMvc.perform(get("/medications/brand/Calpol"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateMedication() throws Exception {
        Medication medication = new Medication(1, "Paracetamol", "Calpol", "Pain reliever");
        when(medicationService.createMedication(any(Medication.class))).thenReturn(medication);
        mockMvc.perform(post("/medications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\":1,\"name\":\"Paracetamol\",\"brand\":\"Calpol\",\"description\":\"Pain reliever\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateMedication() throws Exception {
        Medication updatedMedication = new Medication(1, "Paracetamol Updated", "Calpol Plus", "Strong pain reliever");
        when(medicationService.updateMedication(eq(1), any(Medication.class))).thenReturn(updatedMedication);
        mockMvc.perform(put("/medications/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\":1,\"name\":\"Paracetamol Updated\",\"brand\":\"Calpol Plus\",\"description\":\"Strong pain reliever\"}"))
                .andExpect(status().isOk());
    }
}