package com.example.hms.service;

import com.example.hms.entity.Physician;
import com.example.hms.repository.PhysicianRepository;
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

public class PhysicianServiceTest {

    @Mock
    private PhysicianRepository physicianRepository;

    @InjectMocks
    private PhysicianService physicianService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllPhysicians() {
        Physician p1 = new Physician(1, "John Dorian", "Staff Internist", 111111111);
        Physician p2 = new Physician(2, "Elliot Reid", "Attending Physician", 222222222);
        when(physicianRepository.findAll()).thenReturn(List.of(p1, p2));
        List<Physician> result = physicianService.getAllPhysicians();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetPhysicianById() {
        Physician p = new Physician(1, "John Dorian", "Staff Internist", 111111111);
        when(physicianRepository.findById(1)).thenReturn(Optional.of(p));
        Physician result = physicianService.getPhysicianById(1);
        assertEquals("John Dorian", result.getName());
    }

    @Test
    public void testGetPhysicianByName() {
        Physician p1 = new Physician(1, "John Dorian", "Staff Internist", 111111111);
        when(physicianRepository.findByName("John Dorian")).thenReturn(List.of(p1));
        List<Physician> result = physicianService.getPhysicianByName("John Dorian");
        assertEquals(1, result.size());
        assertEquals("John Dorian", result.get(0).getName());
    }

    @Test
    public void testGetPhysicianByPosition() {
        Physician p1 = new Physician(1, "John Dorian", "Staff Internist", 111111111);
        when(physicianRepository.findByPosition("Staff Internist")).thenReturn(List.of(p1));
        List<Physician> result = physicianService.getPhysicianByPosition("Staff Internist");
        assertEquals(1, result.size());
        assertEquals("Staff Internist", result.get(0).getPosition());
    }

    @Test
    public void testAddPhysician() {
        Physician physician = new Physician(0, "John Dorian", "Staff Internist", 111111111);
        when(physicianRepository.save(physician)).thenReturn(new Physician(1, "John Dorian", "Staff Internist", 111111111));
        Physician result = physicianService.addPhysician(physician);
        assertEquals(1, result.getEmployeeId());
    }

    @Test
    public void testUpdatePhysician() {
        Physician existingPhysician = new Physician(1, "John Dorian", "Staff Internist", 111111111);
        Physician updatedPhysician = new Physician(1, "John Dorian", "Senior Attending", 111111111);

        when(physicianRepository.findById(1)).thenReturn(Optional.of(existingPhysician));
        when(physicianRepository.save(existingPhysician)).thenReturn(updatedPhysician);

        Physician result = physicianService.updatePhysician(1, updatedPhysician);
        assertEquals("John Dorian", result.getName());
        assertEquals("Senior Attending", result.getPosition());
    }

    @Test
    public void testUpdatePhysician_NotFound() {
        Physician updatedPhysician = new Physician(1, "John Dorian", "Staff Internist", 111111111);
        when(physicianRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> physicianService.updatePhysician(99, updatedPhysician));
        assertEquals("Physician not found", ex.getMessage());
    }
}