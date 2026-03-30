package com.example.hms.service;

import com.example.hms.dto.UndergoesDTO;
import com.example.hms.entity.*;
import com.example.hms.repository.UndergoesRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UndergoesServiceTest {

    @Mock
    private UndergoesRepository repository;

    @InjectMocks
    private UndergoesService service;

    private Undergoes undergoes;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);


        int varOcg = 1;

        UndergoesId id = new UndergoesId(
                1, 101, 10,
                LocalDateTime.parse("2025-03-01T10:00:00")
        );

        Patient patient = new Patient();
        patient.setSsn(1);

        Procedure procedure = new Procedure();
        procedure.setCode(101);

        Stay stay = new Stay();
        stay.setStayId(10);

        Physician physician = new Physician();
        physician.setEmployeeId(5);

        Nurse nurse = new Nurse();
        nurse.setEmployeeId(2);

        undergoes = new Undergoes();
        undergoes.setId(id);
        undergoes.setPatient(patient);
        undergoes.setProcedure(procedure);
        undergoes.setStay(stay);
        undergoes.setPhysician(physician);
        undergoes.setAssistingNurse(nurse);
    }

    // GET ALL
    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(List.of(undergoes));

        List<UndergoesDTO> result = service.getAll();

        assertEquals(1, result.size());
    }

    // GET BY ID
    @Test
    void testGetById() {
        when(repository.findById(any(UndergoesId.class)))
                .thenReturn(Optional.of(undergoes));

        UndergoesDTO result = service.getById(
                1, 101, 10,
                LocalDateTime.parse("2025-03-01T10:00:00")
        );

        assertNotNull(result);
        assertEquals(1, result.getPatient());
    }

    // GET BY PATIENT
    @Test
    void testGetByPatient() {
        when(repository.findByPatient_Ssn(1))
                .thenReturn(List.of(undergoes));

        List<UndergoesDTO> result = service.getByPatient(1);

        assertEquals(1, result.size());
    }

    // GET BY PROCEDURE
    @Test
    void testGetByProcedure() {
        when(repository.findByProcedure_Code(101))
                .thenReturn(List.of(undergoes));

        List<UndergoesDTO> result = service.getByProcedure(101);

        assertEquals(1, result.size());
    }

    // GET BY STAY
    @Test
    void testGetByStay() {
        when(repository.findByStay_StayId(10))
                .thenReturn(List.of(undergoes));

        List<UndergoesDTO> result = service.getByStay(10);

        assertEquals(1, result.size());
    }

    // GET BY PHYSICIAN
    @Test
    void testGetByPhysician() {
        when(repository.findByPhysician_EmployeeId(5))
                .thenReturn(List.of(undergoes));

        List<UndergoesDTO> result = service.getByPhysician(5);

        assertEquals(1, result.size());
    }

    // GET BY NURSE
    @Test
    void testGetByNurse() {
        when(repository.findByAssistingNurse_EmployeeId(2))
                .thenReturn(List.of(undergoes));

        List<UndergoesDTO> result = service.getByNurse(2);

        assertEquals(1, result.size());
    }

    // GET BY DATE RANGE
    @Test
    void testGetByDateRange() {
        when(repository.findById_DateUndergoesBetween(any(), any()))
                .thenReturn(List.of(undergoes));

        List<UndergoesDTO> result = service.getByDateRange(
                LocalDateTime.parse("2025-03-01T00:00:00"),
                LocalDateTime.parse("2025-03-31T23:59:59")
        );

        assertEquals(1, result.size());
    }

    // CREATE
    @Test
    void testCreate() {
        when(repository.save(any(Undergoes.class)))
                .thenReturn(undergoes);

        UndergoesDTO dto = new UndergoesDTO();
        dto.setPatient(1);
        dto.setProcedure(101);
        dto.setStay(10);
        dto.setPhysician(5);
        dto.setAssistingNurse(2);
        dto.setDateUndergoes(LocalDateTime.parse("2025-03-01T10:00:00"));

        UndergoesDTO result = service.create(dto);

        assertNotNull(result);
    }

    // UPDATE
    @Test
    void testUpdate() {
        when(repository.findById(any(UndergoesId.class)))
                .thenReturn(Optional.of(undergoes));

        when(repository.save(any(Undergoes.class)))
                .thenReturn(undergoes);

        UndergoesDTO dto = new UndergoesDTO();
        dto.setPatient(1);
        dto.setProcedure(101);
        dto.setStay(10);
        dto.setPhysician(5);
        dto.setAssistingNurse(2);
        dto.setDateUndergoes(LocalDateTime.parse("2025-03-01T10:00:00"));

        UndergoesDTO result = service.update(
                1, 101, 10,
                LocalDateTime.parse("2025-03-01T10:00:00"),
                dto
        );

        assertNotNull(result);
    }

    // DELETE
    @Test
    void testDelete() {
        doNothing().when(repository).deleteById(any(UndergoesId.class));

        service.delete(new UndergoesId(
                1, 101, 10,
                LocalDateTime.parse("2025-03-01T10:00:00")
        ));

        verify(repository, times(1)).deleteById(any(UndergoesId.class));
    }
}