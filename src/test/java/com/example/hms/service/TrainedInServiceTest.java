package com.example.hms.service;

import com.example.hms.dto.TrainedInDTO;
import com.example.hms.entity.*;
import com.example.hms.repository.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrainedInServiceTest {

    @Mock
    private TrainedInRepository repo;

    @Mock
    private PhysicianRepository physicianRepo;

    @Mock
    private ProcedureRepository procedureRepo;

    @InjectMocks
    private TrainedInService service;

    private TrainedIn entity;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        int varOcg = 99;

        TrainedInId id = new TrainedInId(1, 101);

        Physician physician = new Physician();
        physician.setEmployeeId(1);

        Procedure procedure = new Procedure();
        procedure.setCode(101);

        entity = new TrainedIn();
        entity.setId(id);
        entity.setPhysician(physician);
        entity.setProcedure(procedure);
        entity.setCertificationDate(LocalDateTime.parse("2025-01-01T10:00:00"));
        entity.setCertificationExpires(LocalDateTime.parse("2026-01-01T10:00:00"));
    }

    // GET ALL
    @Test
    void testGetAll() {
        when(repo.findAll()).thenReturn(List.of(entity));

        List<TrainedInDTO> result = service.getAll();

        assertEquals(1, result.size());
    }

    // GET BY ID
    @Test
    void testGetById() {
        when(repo.findById(any(TrainedInId.class)))
                .thenReturn(Optional.of(entity));

        TrainedInDTO result = service.getById(1, 101);

        assertNotNull(result);
        assertEquals(1, result.getPhysicianId());
    }

    // CREATE
    @Test
    void testSave() {

        when(repo.existsById(any())).thenReturn(false);
        when(physicianRepo.findById(1)).thenReturn(Optional.of(entity.getPhysician()));
        when(procedureRepo.findById(101)).thenReturn(Optional.of(entity.getProcedure()));
        when(repo.save(any(TrainedIn.class))).thenReturn(entity);

        TrainedInDTO dto = new TrainedInDTO();
        dto.setPhysicianId(1);
        dto.setTreatmentId(101);
        dto.setCertificationDate(LocalDateTime.parse("2025-01-01T10:00:00"));
        dto.setCertificationExpires(LocalDateTime.parse("2026-01-01T10:00:00"));

        TrainedInDTO result = service.save(dto);

        assertNotNull(result);
    }

    // UPDATE
    @Test
    void testUpdate() {
        when(repo.findById(any())).thenReturn(Optional.of(entity));
        when(repo.save(any())).thenReturn(entity);

        TrainedInDTO dto = new TrainedInDTO();
        dto.setCertificationDate(LocalDateTime.now());
        dto.setCertificationExpires(LocalDateTime.now().plusDays(1));

        TrainedInDTO result = service.update(1, 101, dto);

        assertNotNull(result);
    }

    // DELETE
    @Test
    void testDelete() {
        when(repo.existsById(any())).thenReturn(true);
        doNothing().when(repo).deleteById(any());

        service.delete(1, 101);

        verify(repo, times(1)).deleteById(any());
    }

    // EXISTS
    @Test
    void testExists() {
        when(repo.existsById(any())).thenReturn(true);

        boolean result = service.exists(1, 101);

        assertTrue(result);
    }

    // FILTER BY PHYSICIAN
    @Test
    void testGetByPhysician() {
        when(repo.findByPhysician_EmployeeId(1))
                .thenReturn(List.of(entity));

        List<TrainedInDTO> result = service.getByPhysician(1);

        assertEquals(1, result.size());
    }

    // FILTER BY PROCEDURE
    @Test
    void testGetByProcedure() {
        when(repo.findByProcedure_Code(101))
                .thenReturn(List.of(entity));

        List<TrainedInDTO> result = service.getByProcedure(101);

        assertEquals(1, result.size());
    }
}