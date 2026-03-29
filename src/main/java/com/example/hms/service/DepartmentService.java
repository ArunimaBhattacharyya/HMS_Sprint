package com.example.hms.service;

import com.example.hms.dto.DepartmentRequest;
import com.example.hms.dto.DepartmentResponse;
import com.example.hms.entity.Department;
import com.example.hms.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    // Helper method to map Entity to DTO
    private DepartmentResponse mapToResponse(Department department) {
        if (department == null) return null;

        DepartmentResponse response = new DepartmentResponse();
        response.setDepartmentId(department.getDepartmentId());
        response.setName(department.getName());
        response.setHead(department.getHead());

        // Extract the physician's name if the relationship exists
        if (department.getHeadPhysician() != null) {
            response.setHeadPhysicianName(department.getHeadPhysician().getName());
        }

        return response;
    }

    public List<DepartmentResponse> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public DepartmentResponse getDepartmentById(int id) {
        Department department = departmentRepository.findById(id).orElse(null);
        return mapToResponse(department);
    }

    public List<DepartmentResponse> getDepartmentByName(String name){
        return departmentRepository.findByName(name)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<DepartmentResponse> getDepartmentByHead(int head){
        return departmentRepository.findByHead(head)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public DepartmentResponse addDepartment(DepartmentRequest request) {
        Department department = new Department();
        department.setDepartmentId(request.getDepartmentId());
        department.setName(request.getName());
        department.setHead(request.getHead());

        Department savedDepartment = departmentRepository.save(department);
        return mapToResponse(savedDepartment);
    }

    public DepartmentResponse updateDepartment(int id, DepartmentRequest request) {
        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        existingDepartment.setName(request.getName());
        existingDepartment.setHead(request.getHead());

        Department updatedDepartment = departmentRepository.save(existingDepartment);
        return mapToResponse(updatedDepartment);
    }

    public void deleteDepartment(int id) {
        if(!departmentRepository.existsById(id)){
            throw new RuntimeException("Department not found. ID: " + id);
        }
        departmentRepository.deleteById(id);
    }
}