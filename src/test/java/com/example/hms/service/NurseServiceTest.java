package com.example.hms.service;

import com.example.hms.entity.Nurse;
import com.example.hms.repository.NurseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

public class NurseServiceTest {

    @Mock
    private NurseRepository nurseRepository;

    @InjectMocks
    private NurseService nurseService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllNurses() {
        Nurse n1 = new Nurse(1, "Arunima", "Head Nurse", 1, 100000001);
        Nurse n2 = new Nurse(2, "Bidwattam", "Staff Nurse", 0, 100000000);
        when(nurseRepository.findAll()).thenReturn(List.of(n1, n2));
        List<Nurse> result = nurseService.getAllNurses();
        assertEquals(2, result.size());
//        verify(nurseRepository).findAll();
    }

    @Test
    public void testGetNurseById() {
        Nurse n = new Nurse(1, "Arunima", "Head Nurse", 1, 10000000);
        when(nurseRepository.findById(1)).thenReturn(Optional.of(n));
        Optional<Nurse> result = nurseService.getNurseById(1);
        assertEquals("Arunima", result.get().getName());
    }

    @Test
    public void testGetNurseByName() {
        Nurse n1 = new Nurse(1, "Arunima", "Head Nurse", 1, 100000001);
        Nurse n2 = new Nurse(2, "Bidwattam", "Staff Nurse", 0, 100000002);
        when(nurseRepository.findByName("Arunima")).thenReturn(List.of(n1, n2));
        List<Nurse> result = nurseService.getNurseByName("Arunima");
        assertEquals(2, result.size());
    }

    @Test
    public void testGetNurseByPosition() {
        Nurse n = new Nurse(1, "Arunima", "Head Nurse", 1, 100000001);
        when(nurseRepository.findByPosition("Head Nurse")).thenReturn(List.of(n));
        List<Nurse> result = nurseService.getNurseByPosition("Head Nurse");
        assertEquals(1, result.size());
        assertEquals("Head Nurse", result.get(0).getPosition());
    }

    @Test
    public void testCreateNurse() {
        Nurse nurse = new Nurse(0, "Arunima", "Head Nurse", 1, 100000001);
        when(nurseRepository.save(nurse)).thenReturn(new Nurse(1, "Arunima", "Head Nurse", 1, 100000001));
        Nurse result = nurseService.createNurse(nurse);
        assertEquals(1, result.getEmployeeId());
    }

    @Test
    public void testUpdateNurse() {
        Nurse existingNurse = new Nurse(1, "Arunima", "Head Nurse", 1, 100000001);
        Nurse updatedNurse = new Nurse(1, "Arunima Bhattacharyya", "Senior Nurse", 1, 100000001);
        when(nurseRepository.findById(1)).thenReturn(Optional.of(existingNurse));
        when(nurseRepository.save(existingNurse)).thenReturn(updatedNurse);
        Nurse result = nurseService.updateNurse(1, updatedNurse);
        assertEquals("Arunima Bhattacharyya", result.getName());
        assertEquals("Senior Nurse", result.getPosition());
    }

    @Test
    public void testUpdateNurse_NotFound() {
        Nurse updatedNurse = new Nurse(1, "Arunima", "Head Nurse", 1, 100000001);
        when(nurseRepository.findById(99)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> nurseService.updateNurse(99, updatedNurse));
        assertEquals("Nurse not found", ex.getMessage());
    }


}
