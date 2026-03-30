package com.example.hms.service;

import com.example.hms.dto.PrescribesRequest;
import com.example.hms.dto.PrescribesResponse;
import com.example.hms.entity.Appointment;
import com.example.hms.entity.Medication;
import com.example.hms.entity.Patient;
import com.example.hms.entity.Physician;
import com.example.hms.entity.Prescribes;
import com.example.hms.entity.PrescribesId;
import com.example.hms.repository.AppointmentRepository;
import com.example.hms.repository.MedicationRepository;
import com.example.hms.repository.PatientRepository;
import com.example.hms.repository.PhysicianRepository;
import com.example.hms.repository.PrescribesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PrescribesServiceTest {

    @Mock
    private PrescribesRepository prescribesRepository;

    @Mock
    private PhysicianRepository physicianRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private MedicationRepository medicationRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private PrescribesService prescribesService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {
        LocalDateTime date = LocalDateTime.of(2008, 4, 24, 10, 47);
        PrescribesRequest request = buildRequest(1, 100000001, 1, date, 13216584, "5");

        Physician physician = new Physician(1, "John Dorian", "Attending", 100000111);
        Patient patient = buildPatient(100000001, "John Smith");
        Medication medication = new Medication(1, "Procrastin-X", "PX", "desc");
        Appointment appointment = buildAppointment(13216584);

        when(physicianRepository.findById(1)).thenReturn(Optional.of(physician));
        when(patientRepository.findById(100000001)).thenReturn(Optional.of(patient));
        when(medicationRepository.findById(1)).thenReturn(Optional.of(medication));
        when(appointmentRepository.findById(13216584)).thenReturn(Optional.of(appointment));
        when(prescribesRepository.save(any(Prescribes.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PrescribesResponse response = prescribesService.create(request);

        assertEquals(1, response.getPhysicianId());
        assertEquals(100000001, response.getPatientSsn());
        assertEquals(1, response.getMedicationCode());
        assertEquals("5", response.getDose());
        assertEquals(13216584, response.getAppointmentId());
    }

    @Test
    public void testCreate_DoseRequired() {
        PrescribesRequest request = buildRequest(1, 100000001, 1,
                LocalDateTime.of(2008, 4, 24, 10, 47), 13216584, " ");

        Physician physician = new Physician(1, "John Dorian", "Attending", 100000111);
        Patient patient = buildPatient(100000001, "John Smith");
        Medication medication = new Medication(1, "Procrastin-X", "PX", "desc");
        Appointment appointment = buildAppointment(13216584);

        when(physicianRepository.findById(1)).thenReturn(Optional.of(physician));
        when(patientRepository.findById(100000001)).thenReturn(Optional.of(patient));
        when(medicationRepository.findById(1)).thenReturn(Optional.of(medication));
        when(appointmentRepository.findById(13216584)).thenReturn(Optional.of(appointment));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> prescribesService.create(request));
        assertEquals("Dose is required", ex.getMessage());
    }

    @Test
    public void testGetAll() {
        Prescribes p1 = buildPrescribes(1, 100000001, 1,
                LocalDateTime.of(2008, 4, 24, 10, 47), "5", 13216584,
                "John Dorian", "John Smith", "Procrastin-X");

        Prescribes p2 = buildPrescribes(9, 100000004, 2,
                LocalDateTime.of(2008, 4, 27, 10, 53), "10", 86213939,
                "Molly Clock", "Dennis Doe", "Thesisin");

        when(prescribesRepository.findAll()).thenReturn(List.of(p1, p2));

        List<PrescribesResponse> response = prescribesService.getAll();
        assertEquals(2, response.size());
    }

    @Test
    public void testGetById() {
        LocalDateTime date = LocalDateTime.of(2008, 4, 24, 10, 47);
        Prescribes prescribes = buildPrescribes(1, 100000001, 1, date, "5", 13216584,
                "John Dorian", "John Smith", "Procrastin-X");

        when(prescribesRepository.findById(new PrescribesId(1, 100000001, 1, date)))
                .thenReturn(Optional.of(prescribes));

        PrescribesResponse response = prescribesService.getById(1, 100000001, 1, date);
        assertEquals("John Smith", response.getPatientName());
    }

    @Test
    public void testSearchByPhysicianPatientMedication() {
        Prescribes prescribes = buildPrescribes(1, 100000001, 1,
                LocalDateTime.of(2008, 4, 24, 10, 47), "5", 13216584,
                "John Dorian", "John Smith", "Procrastin-X");

        when(prescribesRepository.findByIdPhysicianAndIdPatientAndIdMedication(1, 100000001, 1))
                .thenReturn(List.of(prescribes));

        List<PrescribesResponse> response = prescribesService.getByPhysicianPatientMedication(1, 100000001, 1);
        assertEquals(1, response.size());
        assertEquals(1, response.get(0).getMedicationCode());
    }

    @Test
    public void testUpdate_DoseAndAppointmentSameMedication() {
        LocalDateTime date = LocalDateTime.of(2008, 4, 24, 10, 47);
        PrescribesId id = new PrescribesId(1, 100000001, 1, date);

        Prescribes existing = buildPrescribes(1, 100000001, 1, date, "5", 13216584,
                "John Dorian", "John Smith", "Procrastin-X");

        PrescribesRequest request = buildRequest(1, 100000001, 0, date, 86213939, "7");

        Medication medication = new Medication(1, "Procrastin-X", "PX", "desc");
        Appointment appointment = buildAppointment(86213939);

        when(prescribesRepository.findById(id)).thenReturn(Optional.of(existing));
        when(medicationRepository.findById(1)).thenReturn(Optional.of(medication));
        when(appointmentRepository.findById(86213939)).thenReturn(Optional.of(appointment));
        when(prescribesRepository.save(existing)).thenReturn(existing);

        PrescribesResponse response = prescribesService.update(1, 100000001, 1, date, request);

        assertEquals("7", response.getDose());
        assertEquals(86213939, response.getAppointmentId());
    }

    @Test
    public void testUpdate_ChangeMedicationAndDose() {
        LocalDateTime date = LocalDateTime.of(2008, 4, 24, 10, 47);
        PrescribesId oldId = new PrescribesId(1, 100000001, 1, date);
        PrescribesId newId = new PrescribesId(1, 100000001, 10, date);

        Prescribes existing = buildPrescribes(1, 100000001, 1, date, "5", 13216584,
                "John Dorian", "John Smith", "Procrastin-X");

        PrescribesRequest request = buildRequest(1, 100000001, 10, date, 13216584, "8");

        Medication newMedication = new Medication(10, "Paracetamol", "Calpol", "desc");
        Appointment appointment = buildAppointment(13216584);

        when(prescribesRepository.findById(oldId)).thenReturn(Optional.of(existing));
        when(medicationRepository.findById(10)).thenReturn(Optional.of(newMedication));
        when(prescribesRepository.existsById(newId)).thenReturn(false);
        when(appointmentRepository.findById(13216584)).thenReturn(Optional.of(appointment));
        when(prescribesRepository.save(any(Prescribes.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PrescribesResponse response = prescribesService.update(1, 100000001, 1, date, request);

        verify(prescribesRepository).delete(existing);
        assertEquals(10, response.getMedicationCode());
        assertEquals("8", response.getDose());
    }

    @Test
    public void testUpdate_ChangeMedicationConflict() {
        LocalDateTime date = LocalDateTime.of(2008, 4, 24, 10, 47);
        PrescribesId oldId = new PrescribesId(1, 100000001, 1, date);
        PrescribesId newId = new PrescribesId(1, 100000001, 10, date);

        Prescribes existing = buildPrescribes(1, 100000001, 1, date, "5", 13216584,
                "John Dorian", "John Smith", "Procrastin-X");

        PrescribesRequest request = buildRequest(1, 100000001, 10, date, 13216584, "8");

        Medication newMedication = new Medication(10, "Paracetamol", "Calpol", "desc");

        when(prescribesRepository.findById(oldId)).thenReturn(Optional.of(existing));
        when(medicationRepository.findById(10)).thenReturn(Optional.of(newMedication));
        when(prescribesRepository.existsById(newId)).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> prescribesService.update(1, 100000001, 1, date, request));

        assertEquals("Prescribes record already exists for target medication", ex.getMessage());
    }

    @Test
    public void testDelete() {
        LocalDateTime date = LocalDateTime.of(2008, 4, 24, 10, 47);
        PrescribesId id = new PrescribesId(1, 100000001, 1, date);

        when(prescribesRepository.existsById(id)).thenReturn(true);

        prescribesService.delete(1, 100000001, 1, date);

        verify(prescribesRepository).deleteById(id);
    }

    @Test
    public void testDelete_NotFound() {
        LocalDateTime date = LocalDateTime.of(2008, 4, 24, 10, 47);
        PrescribesId id = new PrescribesId(1, 100000001, 1, date);

        when(prescribesRepository.existsById(id)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> prescribesService.delete(1, 100000001, 1, date));
        assertEquals("Prescribes record not found", ex.getMessage());
    }

    private PrescribesRequest buildRequest(int physicianId, int patientSsn, int medicationCode,
                                           LocalDateTime date, Integer appointmentId, String dose) {
        PrescribesRequest request = new PrescribesRequest();
        request.setPhysicianId(physicianId);
        request.setPatientSsn(patientSsn);
        request.setMedicationCode(medicationCode);
        request.setDate(date);
        request.setAppointmentId(appointmentId);
        request.setDose(dose);
        return request;
    }

    private Prescribes buildPrescribes(int physicianId, int patientSsn, int medicationCode,
                                       LocalDateTime date, String dose, Integer appointmentId,
                                       String physicianName, String patientName, String medicationName) {
        Prescribes p = new Prescribes();
        p.setId(new PrescribesId(physicianId, patientSsn, medicationCode, date));

        Physician physician = new Physician(physicianId, physicianName, "Attending", 100000111);
        p.setPhysician(physician);

        Patient patient = buildPatient(patientSsn, patientName);
        p.setPatient(patient);

        Medication medication = new Medication(medicationCode, medicationName, "Brand", "Desc");
        p.setMedication(medication);

        if (appointmentId != null) {
            p.setAppointment(buildAppointment(appointmentId));
        }

        p.setDose(dose);
        return p;
    }

    private Patient buildPatient(int ssn, String name) {
        Patient patient = new Patient();
        patient.setSsn(ssn);
        patient.setName(name);
        return patient;
    }

    private Appointment buildAppointment(int appointmentId) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(appointmentId);
        return appointment;
    }
}

