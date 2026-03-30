package com.example.hms.service;

import com.example.hms.dto.AppointmentRequest;
import com.example.hms.dto.AppointmentResponse;
import com.example.hms.entity.Appointment;
import com.example.hms.entity.Nurse;
import com.example.hms.entity.Patient;
import com.example.hms.entity.Physician;
import com.example.hms.repository.AppointmentRepository;
import com.example.hms.repository.NurseRepository;
import com.example.hms.repository.PatientRepository;
import com.example.hms.repository.PhysicianRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private NurseRepository nurseRepository;

    @Mock
    private PhysicianRepository physicianRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAppointment() {
        AppointmentRequest request = buildRequest(13216584, 100000001, 101, 1,
                LocalDateTime.of(2008, 4, 24, 10, 47),
                LocalDateTime.of(2008, 4, 24, 11, 47),
                "A");

        Patient patient = buildPatient(100000001, "John Smith");
        Physician physician = new Physician(1, "John Dorian", "Attending", 100000111);
        Nurse nurse = new Nurse(101, "Nina", "Head Nurse", 1, 100000010);

        Appointment saved = buildAppointment(request, patient, nurse, physician);

        when(patientRepository.findById(100000001)).thenReturn(Optional.of(patient));
        when(physicianRepository.findById(1)).thenReturn(Optional.of(physician));
        when(nurseRepository.findById(101)).thenReturn(Optional.of(nurse));
        when(appointmentRepository.save(org.mockito.ArgumentMatchers.any(Appointment.class))).thenReturn(saved);

        AppointmentResponse response = appointmentService.create(request);

        assertEquals(13216584, response.getAppointmentId());
        assertEquals("John Smith", response.getPatientName());
        assertEquals("John Dorian", response.getPhysicianName());
        assertEquals("Nina", response.getPrepNurseName());
    }

    @Test
    public void testCreateAppointmentWithoutNurse() {
        AppointmentRequest request = buildRequest(86213939, 100000004, null, 9,
                LocalDateTime.of(2008, 4, 27, 10, 53),
                LocalDateTime.of(2008, 4, 27, 11, 53),
                "B");

        Patient patient = buildPatient(100000004, "Dennis Doe");
        Physician physician = new Physician(9, "Molly Clock", "Senior", 100000222);

        Appointment saved = buildAppointment(request, patient, null, physician);

        when(patientRepository.findById(100000004)).thenReturn(Optional.of(patient));
        when(physicianRepository.findById(9)).thenReturn(Optional.of(physician));
        when(appointmentRepository.save(org.mockito.ArgumentMatchers.any(Appointment.class))).thenReturn(saved);

        AppointmentResponse response = appointmentService.create(request);

        assertNull(response.getPrepNurseId());
        assertNull(response.getPrepNurseName());
    }

    @Test
    public void testCreateAppointment_InvalidTimeWindow() {
        AppointmentRequest request = buildRequest(13216585, 100000001, null, 1,
                LocalDateTime.of(2008, 4, 24, 12, 0),
                LocalDateTime.of(2008, 4, 24, 11, 0),
                "A");

        Patient patient = buildPatient(100000001, "John Smith");
        Physician physician = new Physician(1, "John Dorian", "Attending", 100000111);

        when(patientRepository.findById(100000001)).thenReturn(Optional.of(patient));
        when(physicianRepository.findById(1)).thenReturn(Optional.of(physician));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> appointmentService.create(request));
        assertEquals("Invalid appointment time window", ex.getMessage());
    }

    @Test
    public void testGetAllAppointments() {
        Patient patient = buildPatient(100000001, "John Smith");
        Physician physician = new Physician(1, "John Dorian", "Attending", 100000111);
        Nurse nurse = new Nurse(101, "Nina", "Head Nurse", 1, 100000010);

        Appointment a1 = buildAppointment(buildRequest(1, 100000001, 101, 1,
                LocalDateTime.of(2008, 4, 24, 10, 0), LocalDateTime.of(2008, 4, 24, 11, 0), "A"),
                patient, nurse, physician);
        Appointment a2 = buildAppointment(buildRequest(2, 100000001, null, 1,
                LocalDateTime.of(2008, 4, 24, 12, 0), LocalDateTime.of(2008, 4, 24, 13, 0), "B"),
                patient, null, physician);

        when(appointmentRepository.findAll()).thenReturn(List.of(a1, a2));

        List<AppointmentResponse> result = appointmentService.getAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetById() {
        Patient patient = buildPatient(100000001, "John Smith");
        Physician physician = new Physician(1, "John Dorian", "Attending", 100000111);

        Appointment appointment = buildAppointment(buildRequest(13216584, 100000001, null, 1,
                LocalDateTime.of(2008, 4, 24, 10, 47), LocalDateTime.of(2008, 4, 24, 11, 47), "A"),
                patient, null, physician);

        when(appointmentRepository.findById(13216584)).thenReturn(Optional.of(appointment));

        AppointmentResponse result = appointmentService.getById(13216584);
        assertEquals(13216584, result.getAppointmentId());
        assertEquals("John Smith", result.getPatientName());
    }

    @Test
    public void testGetByPatient() {
        Patient patient = buildPatient(100000001, "John Smith");
        Physician physician = new Physician(1, "John Dorian", "Attending", 100000111);

        Appointment appointment = buildAppointment(buildRequest(13216584, 100000001, null, 1,
                LocalDateTime.of(2008, 4, 24, 10, 47), LocalDateTime.of(2008, 4, 24, 11, 47), "A"),
                patient, null, physician);

        when(appointmentRepository.findByPatientSsn(100000001)).thenReturn(List.of(appointment));

        List<AppointmentResponse> result = appointmentService.getByPatient(100000001);
        assertEquals(1, result.size());
        assertEquals(100000001, result.get(0).getPatientSsn());
    }

    @Test
    public void testGetByNurse() {
        Patient patient = buildPatient(100000001, "John Smith");
        Physician physician = new Physician(1, "John Dorian", "Attending", 100000111);
        Nurse nurse = new Nurse(101, "Nina", "Head Nurse", 1, 100000010);

        Appointment appointment = buildAppointment(buildRequest(13216584, 100000001, 101, 1,
                LocalDateTime.of(2008, 4, 24, 10, 47), LocalDateTime.of(2008, 4, 24, 11, 47), "A"),
                patient, nurse, physician);

        when(appointmentRepository.findByPrepNurseId(101)).thenReturn(List.of(appointment));

        List<AppointmentResponse> result = appointmentService.getByNurse(101);
        assertEquals(1, result.size());
        assertEquals(101, result.get(0).getPrepNurseId());
    }

    @Test
    public void testGetByPhysician() {
        Patient patient = buildPatient(100000001, "John Smith");
        Physician physician = new Physician(1, "John Dorian", "Attending", 100000111);

        Appointment appointment = buildAppointment(buildRequest(13216584, 100000001, null, 1,
                LocalDateTime.of(2008, 4, 24, 10, 47), LocalDateTime.of(2008, 4, 24, 11, 47), "A"),
                patient, null, physician);

        when(appointmentRepository.findByPhysicianId(1)).thenReturn(List.of(appointment));

        List<AppointmentResponse> result = appointmentService.getByPhysician(1);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getPhysicianId());
    }

    @Test
    public void testUpdateAppointment() {
        Appointment existing = new Appointment();
        existing.setAppointmentId(13216584);

        AppointmentRequest request = buildRequest(13216584, 100000004, null, 9,
                LocalDateTime.of(2008, 4, 27, 10, 53),
                LocalDateTime.of(2008, 4, 27, 11, 53),
                "B");

        Patient patient = buildPatient(100000004, "Dennis Doe");
        Physician physician = new Physician(9, "Molly Clock", "Senior", 100000222);

        Appointment saved = buildAppointment(request, patient, null, physician);

        when(appointmentRepository.findById(13216584)).thenReturn(Optional.of(existing));
        when(patientRepository.findById(100000004)).thenReturn(Optional.of(patient));
        when(physicianRepository.findById(9)).thenReturn(Optional.of(physician));
        when(appointmentRepository.save(existing)).thenReturn(saved);

        AppointmentResponse response = appointmentService.update(13216584, request);

        assertEquals("Dennis Doe", response.getPatientName());
        assertEquals("Molly Clock", response.getPhysicianName());
        assertEquals("B", response.getExaminationRoom());
    }

    @Test
    public void testUpdateAppointment_NotFound() {
        AppointmentRequest request = buildRequest(1, 100000001, null, 1,
                LocalDateTime.of(2008, 4, 24, 10, 0),
                LocalDateTime.of(2008, 4, 24, 11, 0),
                "A");

        when(appointmentRepository.findById(999)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> appointmentService.update(999, request));
        assertEquals("Appointment not found", ex.getMessage());
    }

    @Test
    public void testDeleteAppointment() {
        when(appointmentRepository.existsById(13216584)).thenReturn(true);

        appointmentService.delete(13216584);

        verify(appointmentRepository).deleteById(13216584);
    }

    @Test
    public void testDeleteAppointment_NotFound() {
        when(appointmentRepository.existsById(999)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> appointmentService.delete(999));
        assertEquals("Appointment not found", ex.getMessage());
    }

    private AppointmentRequest buildRequest(int appointmentId,
                                            int patientSsn,
                                            Integer prepNurseId,
                                            int physicianId,
                                            LocalDateTime start,
                                            LocalDateTime end,
                                            String room) {
        AppointmentRequest request = new AppointmentRequest();
        request.setAppointmentId(appointmentId);
        request.setPatientSsn(patientSsn);
        request.setPrepNurseId(prepNurseId);
        request.setPhysicianId(physicianId);
        request.setStart(start);
        request.setEnd(end);
        request.setExaminationRoom(room);
        return request;
    }

    private Appointment buildAppointment(AppointmentRequest request, Patient patient, Nurse nurse, Physician physician) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(request.getAppointmentId());
        appointment.setPatientSsn(request.getPatientSsn());
        appointment.setPatient(patient);
        appointment.setPrepNurseId(request.getPrepNurseId());
        appointment.setPrepNurse(nurse);
        appointment.setPhysicianId(request.getPhysicianId());
        appointment.setPhysician(physician);
        appointment.setStart(request.getStart());
        appointment.setEnd(request.getEnd());
        appointment.setExaminationRoom(request.getExaminationRoom());
        return appointment;
    }

    private Patient buildPatient(int ssn, String name) {
        Patient patient = new Patient();
        patient.setSsn(ssn);
        patient.setName(name);
        return patient;
    }
}

