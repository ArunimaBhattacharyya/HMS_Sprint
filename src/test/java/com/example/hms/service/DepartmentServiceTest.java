package com.example.hms.service;

import com.example.hms.dto.DepartmentRequest;
import com.example.hms.dto.DepartmentResponse;
import com.example.hms.entity.Department;
import com.example.hms.repository.DepartmentRepository;
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

public class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllDepartments() {
        Department d1 = new Department(1, "General Medicine", 4);
        Department d2 = new Department(2, "Surgery", 7);

        when(departmentRepository.findAll()).thenReturn(List.of(d1, d2));
        List<DepartmentResponse> result = departmentService.getAllDepartments();

        assertEquals(2, result.size());
    }

    @Test
    public void testGetDepartmentById() {
        Department d = new Department(1, "General Medicine", 4);

        when(departmentRepository.findById(1)).thenReturn(Optional.of(d));
        DepartmentResponse result = departmentService.getDepartmentById(1);

        assertEquals("General Medicine", result.getName());
        assertEquals(4, result.getHead());
    }

    @Test
    public void testGetDepartmentByName() {
        Department d1 = new Department(1, "General Medicine", 4);

        when(departmentRepository.findByName("General Medicine")).thenReturn(List.of(d1));
        List<DepartmentResponse> result = departmentService.getDepartmentByName("General Medicine");

        assertEquals(1, result.size());
        assertEquals("General Medicine", result.get(0).getName());
    }

    @Test
    public void testGetDepartmentByHead() {
        Department d1 = new Department(1, "General Medicine", 4);

        when(departmentRepository.findByHead(4)).thenReturn(List.of(d1));
        List<DepartmentResponse> result = departmentService.getDepartmentByHead(4);

        assertEquals(1, result.size());
        assertEquals(4, result.get(0).getHead());
    }

    @Test
    public void testAddDepartment() {
        DepartmentRequest request = new DepartmentRequest();
        request.setDepartmentId(1);
        request.setName("General Medicine");
        request.setHead(4);

        Department savedDepartment = new Department(1, "General Medicine", 4);

        when(departmentRepository.save(any(Department.class))).thenReturn(savedDepartment);
        DepartmentResponse result = departmentService.addDepartment(request);

        assertEquals(1, result.getDepartmentId());
        assertEquals("General Medicine", result.getName());
    }

    @Test
    public void testUpdateDepartment() {
        Department existingDepartment = new Department(1, "General Medicine", 4);
        Department updatedDepartment = new Department(1, "Advanced Medicine", 4);

        DepartmentRequest request = new DepartmentRequest();
        request.setName("Advanced Medicine");
        request.setHead(4);

        when(departmentRepository.findById(1)).thenReturn(Optional.of(existingDepartment));
        when(departmentRepository.save(existingDepartment)).thenReturn(updatedDepartment);

        DepartmentResponse result = departmentService.updateDepartment(1, request);
        assertEquals("Advanced Medicine", result.getName());
        assertEquals(4, result.getHead());
    }

    @Test
    public void testUpdateDepartment_NotFound() {
        DepartmentRequest request = new DepartmentRequest();
        request.setName("Advanced Medicine");
        request.setHead(4);

        when(departmentRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> departmentService.updateDepartment(99, request));
        assertEquals("Department not found", ex.getMessage());
    }
}