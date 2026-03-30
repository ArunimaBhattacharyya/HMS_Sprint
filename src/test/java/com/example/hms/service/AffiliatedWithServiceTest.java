package com.example.hms.service;

import com.example.hms.dto.AffiliatedWithRequest;
import com.example.hms.dto.AffiliatedWithResponse;
import com.example.hms.entity.AffiliatedWith;
import com.example.hms.entity.AffiliatedWithId;
import com.example.hms.repository.AffiliatedWithRepository;
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

public class AffiliatedWithServiceTest {

    @Mock
    private AffiliatedWithRepository affiliatedWithRepository;

    @InjectMocks
    private AffiliatedWithService affiliatedWithService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllAffiliations() {
        AffiliatedWith a1 = new AffiliatedWith(1, 101, true);
        AffiliatedWith a2 = new AffiliatedWith(1, 102, false);

        when(affiliatedWithRepository.findAll()).thenReturn(List.of(a1, a2));
        List<AffiliatedWithResponse> result = affiliatedWithService.getAllAffiliations();

        assertEquals(2, result.size());
    }

    @Test
    public void testGetAffiliationById() {
        AffiliatedWith a = new AffiliatedWith(1, 101, true);
        AffiliatedWithId id = new AffiliatedWithId(1, 101);

        when(affiliatedWithRepository.findById(id)).thenReturn(Optional.of(a));
        AffiliatedWithResponse result = affiliatedWithService.getAffiliationById(1, 101);

        assertEquals(1, result.getPhysician());
        assertEquals(101, result.getDepartment());
        assertEquals(true, result.getPrimaryAffiliation());
    }

    @Test
    public void testGetAffiliationsByPhysician() {
        AffiliatedWith a1 = new AffiliatedWith(1, 101, true);

        when(affiliatedWithRepository.findByPhysician(1)).thenReturn(List.of(a1));
        List<AffiliatedWithResponse> result = affiliatedWithService.getAffiliationsByPhysician(1);

        assertEquals(1, result.size());
        assertEquals(101, result.get(0).getDepartment());
    }

    @Test
    public void testGetAffiliationsByDepartment() {
        AffiliatedWith a1 = new AffiliatedWith(1, 101, true);

        when(affiliatedWithRepository.findByDepartment(101)).thenReturn(List.of(a1));
        List<AffiliatedWithResponse> result = affiliatedWithService.getAffiliationsByDepartment(101);

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getPhysician());
    }

    @Test
    public void testAddAffiliation() {
        AffiliatedWithRequest request = new AffiliatedWithRequest();
        request.setPhysician(1);
        request.setDepartment(101);
        request.setPrimaryAffiliation(true);

        AffiliatedWith savedAffiliation = new AffiliatedWith(1, 101, true);

        when(affiliatedWithRepository.save(any(AffiliatedWith.class))).thenReturn(savedAffiliation);
        AffiliatedWithResponse result = affiliatedWithService.addAffiliation(request);

        assertEquals(1, result.getPhysician());
        assertEquals(101, result.getDepartment());
        assertEquals(true, result.getPrimaryAffiliation());
    }

    @Test
    public void testUpdateAffiliation() {
        AffiliatedWith existingAffiliation = new AffiliatedWith(1, 101, true);
        AffiliatedWith updatedAffiliation = new AffiliatedWith(1, 101, false);

        AffiliatedWithRequest request = new AffiliatedWithRequest();
        request.setPrimaryAffiliation(false);

        AffiliatedWithId id = new AffiliatedWithId(1, 101);

        when(affiliatedWithRepository.findById(id)).thenReturn(Optional.of(existingAffiliation));
        when(affiliatedWithRepository.save(existingAffiliation)).thenReturn(updatedAffiliation);

        AffiliatedWithResponse result = affiliatedWithService.updateAffiliation(1, 101, request);
        assertEquals(false, result.getPrimaryAffiliation());
    }

    @Test
    public void testUpdateAffiliation_NotFound() {
        AffiliatedWithRequest request = new AffiliatedWithRequest();
        request.setPrimaryAffiliation(false);

        AffiliatedWithId id = new AffiliatedWithId(99, 999);
        when(affiliatedWithRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> affiliatedWithService.updateAffiliation(99, 999, request));
        assertEquals("Affiliation not found", ex.getMessage());
    }
}