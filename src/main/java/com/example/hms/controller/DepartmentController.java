package com.example.hms.controller;

import com.example.hms.dto.DepartmentRequest;
import com.example.hms.dto.DepartmentResponse;
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
    public List<DepartmentResponse> getAllDepartments() throws Exception {
        return departmentService.getAllDepartments();
    }

    //GET by ID
    @GetMapping("/{id}")
    public DepartmentResponse getDepartmentById(@PathVariable int id) throws Exception {
        return departmentService.getDepartmentById(id);
    }

    // GET /departments/name/{name}
    @GetMapping("/name/{name}")
    public List<DepartmentResponse> getDepartmentByName(@PathVariable String name) {
        return departmentService.getDepartmentByName(name);
    }

    // GET /departments/head/{head}
    @GetMapping("/head/{head}")
    public List<DepartmentResponse> getDepartmentByHead(@PathVariable int head) {
        return departmentService.getDepartmentByHead(head);
    }

    //POST (create)
    @PostMapping
    public DepartmentResponse addDepartment(@RequestBody DepartmentRequest request) throws Exception {
        return departmentService.addDepartment(request);
    }

    //PUT (update)
    @PutMapping("/{id}")
    public DepartmentResponse updateDepartment(@PathVariable int id,
                                               @RequestBody DepartmentRequest request) throws Exception {
        return departmentService.updateDepartment(id, request);
    }

    //DELETE
    @DeleteMapping("/{id}")
    public String deleteDepartment(@PathVariable int id) throws Exception {
        departmentService.deleteDepartment(id);
        return "Department deleted successfully";
    }
}