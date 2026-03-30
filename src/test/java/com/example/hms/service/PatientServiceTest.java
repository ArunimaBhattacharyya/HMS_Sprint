package com.example.hms.service;

import com.example.hms.entity.Patient;
import com.example.hms.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllPatients() {
        Patient p1 = buildPatient(100000001, "John Smith", "Street A", "123456", 1000000001L, 1);
        Patient p2 = buildPatient(100000002, "Jane Doe", "Street B", "654321", 1000000002L, 2);

        when(patientRepository.findAll()).thenReturn(List.of(p1, p2));

        List<Patient> result = patientService.getAllPatients();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetPatientById() {
        Patient patient = buildPatient(100000001, "John Smith", "Street A", "123456", 1000000001L, 1);

        when(patientRepository.findById(100000001)).thenReturn(Optional.of(patient));

        Optional<Patient> result = patientService.getPatientById(100000001);
        assertEquals("John Smith", result.get().getName());
    }

    @Test
    public void testGetPatientByName() {
        Patient p1 = buildPatient(100000001, "John Smith", "Street A", "123456", 1000000001L, 1);
        Patient p2 = buildPatient(100000003, "John Smith", "Street C", "777777", 1000000003L, 3);

        when(patientRepository.findByName("John Smith")).thenReturn(List.of(p1, p2));

        List<Patient> result = patientService.getPatientByName("John Smith");
        assertEquals(2, result.size());
    }

    @Test
    public void testGetPatientByPcp() {
        Patient p1 = buildPatient(100000001, "John Smith", "Street A", "123456", 1000000001L, 1);

        when(patientRepository.findByPcp(1)).thenReturn(List.of(p1));

        List<Patient> result = patientService.getPatientByPcp(1);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getPcp());
    }

    @Test
    public void testCreatePatient() {
        Patient patient = buildPatient(100000006, "Ayan Again 2", "Street F", "999999", 1000000010L, 2);

        when(patientRepository.save(patient)).thenReturn(patient);

        Patient result = patientService.createPatient(patient);
        assertEquals(100000006, result.getSsn());
    }

    @Test
    public void testUpdatePatient() {
        Patient existing = buildPatient(100000001, "John Smith", "Street A", "123456", 1000000001L, 1);
        Patient incoming = buildPatient(100000001, "John Updated", "New Address", "987654", 1000000099L, 9);

        when(patientRepository.findById(100000001)).thenReturn(Optional.of(existing));
        when(patientRepository.save(existing)).thenReturn(existing);

        Patient result = patientService.updatePatient(100000001, incoming);

        assertEquals("John Updated", result.getName());
        assertEquals("New Address", result.getAddress());
        assertEquals("987654", result.getPhone());
        assertEquals(1000000099L, result.getInsuranceId());
        assertEquals(9, result.getPcp());
    }

    @Test
    public void testUpdatePatient_NotFound() {
        Patient incoming = buildPatient(100000001, "John Updated", "New Address", "987654", 1000000099L, 9);

        when(patientRepository.findById(999999999)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> patientService.updatePatient(999999999, incoming));
        assertEquals("Patient not found", ex.getMessage());
    }

    @Test
    public void testDeletePatient() {
        when(patientRepository.existsById(100000001)).thenReturn(true);

        patientService.deletePatient(100000001);

        verify(patientRepository).deleteById(100000001);
    }

    @Test
    public void testDeletePatient_NotFound() {
        when(patientRepository.existsById(999999999)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> patientService.deletePatient(999999999));

        assertEquals("Patient not found: 999999999", ex.getMessage());
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

