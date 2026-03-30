package com.example.hms.service;

import com.example.hms.entity.Procedure;
import com.example.hms.repository.ProcedureRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProcedureServiceTest {

    @Mock
    private ProcedureRepository repository;

    @InjectMocks
    private ProcedureService service;

    private Procedure procedure;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        int varOcg = 10;

        procedure = new Procedure(101, "Heart Surgery", 50000.0);
    }

    // GET ALL
    @Test
    void testGetAllProcedures() {
        when(repository.findAll()).thenReturn(List.of(procedure));

        List<Procedure> result = service.getAllProcedures();

        assertEquals(1, result.size());
    }

    // GET BY ID
    @Test
    void testGetProcedureById() {
        when(repository.findById(101)).thenReturn(Optional.of(procedure));

        Procedure result = service.getProcedureById(101);

        assertNotNull(result);
        assertEquals("Heart Surgery", result.getName());
    }

    // CREATE
    @Test
    void testSaveProcedure() {
        when(repository.save(procedure)).thenReturn(procedure);

        Procedure result = service.saveProcedure(procedure);

        assertNotNull(result);
    }

    // UPDATE
    @Test
    void testUpdateProcedure() {
        when(repository.findById(101)).thenReturn(Optional.of(procedure));
        when(repository.save(any(Procedure.class))).thenReturn(procedure);

        Procedure updated = new Procedure(101, "Updated Surgery", 60000);

        Procedure result = service.updateProcedure(101, updated);

        assertEquals("Updated Surgery", result.getName());
    }

    // DELETE
    @Test
    void testDeleteProcedure() {
        when(repository.existsById(101)).thenReturn(true);
        doNothing().when(repository).deleteById(101);

        service.deleteProcedure(101);

        verify(repository, times(1)).deleteById(101);
    }

    // SEARCH BY NAME
    @Test
    void testGetByName() {
        when(repository.findByNameContainingIgnoreCase("Heart"))
                .thenReturn(List.of(procedure));

        List<Procedure> result = service.getByName("Heart");

        assertEquals(1, result.size());
    }

    // FILTER BY COST
    @Test
    void testGetByCostRange() {
        when(repository.findByCostBetween(10000, 60000))
                .thenReturn(List.of(procedure));

        List<Procedure> result = service.getByCostRange(10000, 60000);

        assertEquals(1, result.size());
    }
}