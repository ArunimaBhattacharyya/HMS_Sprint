package com.example.hms.service;

import com.example.hms.entity.Department;
import com.example.hms.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(int id) {
        return departmentRepository.findById(id).orElse(null);
    }

    // Get department by name
    public List<Department> getDepartmentByName(String name){
        return departmentRepository.findByName(name);
    }

    // Get department by head physician ID
    public List<Department> getDepartmentByHead(int head){
        return departmentRepository.findByHead(head);
    }

    public Department addDepartment(Department department) {
        return departmentRepository.save(department);
    }

    // Update Existing Department
    public Department updateDepartment(int id, Department updatedDepartment) {

        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        existingDepartment.setName(updatedDepartment.getName());
        existingDepartment.setHead(updatedDepartment.getHead());

        return departmentRepository.save(existingDepartment);
    }

    public void deleteDepartment(int id) {
        if(!departmentRepository.existsById(id)){
            throw new RuntimeException("Department not found. ID: " + id);
        }
        departmentRepository.deleteById(id);
    }
}
