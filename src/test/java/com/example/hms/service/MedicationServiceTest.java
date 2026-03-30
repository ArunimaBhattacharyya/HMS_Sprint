package com.example.hms.service;

import com.example.hms.entity.Medication;
import com.example.hms.repository.MedicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class MedicationServiceTest {

    @Mock
    private MedicationRepository medicationRepository;

    @InjectMocks
    private MedicationService medicationService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllMedication() {
        Medication m1 = new Medication(1, "Paracetamol", "Calpol", "Pain reliever");
        Medication m2 = new Medication(2, "Ibuprofen", "Advil", "Anti-inflammatory");
        when(medicationRepository.findAll()).thenReturn(List.of(m1, m2));
        List<Medication> result = medicationService.getAllMedication();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetMedicationByCode() {
        Medication m = new Medication(1, "Paracetamol", "Calpol", "Pain reliever");
        when(medicationRepository.findById(1)).thenReturn(Optional.of(m));
        Optional<Medication> result = medicationService.getMedicaltionByCode(1);
        assertEquals("Paracetamol", result.get().getName());
    }

    @Test
    public void testGetMedicationByName() {
        Medication m1 = new Medication(1, "Paracetamol", "Calpol", "Pain reliever");
        Medication m2 = new Medication(2, "Paracetamol", "Panadol", "Pain reliever");
        when(medicationRepository.findByName("Paracetamol")).thenReturn(List.of(m1, m2));
        List<Medication> result = medicationService.getMedicationByName("Paracetamol");
        assertEquals(2, result.size());
    }

    @Test
    public void testGetMedicationByBrand() {
        Medication m = new Medication(1, "Paracetamol", "Calpol", "Pain reliever");
        when(medicationRepository.findByBrand("Calpol")).thenReturn(List.of(m));
        List<Medication> result = medicationService.getMedicationByBrand("Calpol");
        assertEquals(1, result.size());
        assertEquals("Calpol", result.get(0).getBrand());
    }

    @Test
    public void testCreateMedication() {
        Medication medication = new Medication(0, "Paracetamol", "Calpol", "Pain reliever");
        when(medicationRepository.save(medication)).thenReturn(new Medication(1, "Paracetamol", "Calpol", "Pain reliever"));
        Medication result = medicationService.createMedication(medication);
        assertEquals(1, result.getCode());
    }

    @Test
    public void testUpdateMedication() {
        Medication existingMedication = new Medication(1, "Paracetamol", "Calpol", "Pain reliever");
        Medication updatedMedication = new Medication(1, "Paracetamol Updated", "Calpol Plus", "Strong pain reliever");
        when(medicationRepository.findById(1)).thenReturn(Optional.of(existingMedication));
        when(medicationRepository.save(existingMedication)).thenReturn(updatedMedication);
        Medication result = medicationService.updateMedication(1, updatedMedication);
        assertEquals("Paracetamol Updated", result.getName());
        assertEquals("Calpol Plus", result.getBrand());
    }

    @Test
    public void testUpdateMedication_NotFound() {
        Medication updatedMedication = new Medication(1, "Paracetamol", "Calpol", "Pain reliever");
        when(medicationRepository.findById(99)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> medicationService.updateMedication(99, updatedMedication));
        assertEquals("Medication not found", ex.getMessage());
    }
}
