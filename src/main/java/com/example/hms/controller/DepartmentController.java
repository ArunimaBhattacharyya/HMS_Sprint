package com.example.hms.controller;

import com.example.hms.entity.Department;
import com.example.hms.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    //GET all
    @GetMapping
    public List<Department> getAllDepartments() throws Exception {
        return departmentService.getAllDepartments();
    }

    //GET by ID
    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable int id) throws Exception {
        return departmentService.getDepartmentById(id);
    }

    // GET /departments/name/{name}
    @GetMapping("/name/{name}")
    public List<Department> getDepartmentByName(@PathVariable String name) {
        return departmentService.getDepartmentByName(name);
    }

    // GET /departments/head/{head}
    @GetMapping("/head/{head}")
    public List<Department> getDepartmentByHead(@PathVariable int head) {
        return departmentService.getDepartmentByHead(head);
    }

    //POST (create)
    @PostMapping
    public Department addDepartment(@RequestBody Department department) throws Exception {
        return departmentService.addDepartment(department);
    }

    //PUT (update)
    @PutMapping("/{id}")
    public Department updateDepartment(@PathVariable int id,
                                       @RequestBody Department department) throws Exception {
        return departmentService.updateDepartment(id, department);
    }

    //DELETE
    @DeleteMapping("/{id}")
    public String deleteDepartment(@PathVariable int id) throws Exception {
        departmentService.deleteDepartment(id);
        return "Department deleted successfully";
    }
}
