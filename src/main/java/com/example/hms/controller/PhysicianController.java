package com.example.hms.controller;

import com.example.hms.entity.Physician;
import com.example.hms.service.PhysicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/physicians")
public class PhysicianController {

    @Autowired
    private PhysicianService physicianService;


    // 🔹 GET all
    @GetMapping
    public List<Physician> getAllPhysicians() throws Exception {
        return physicianService.getAllPhysicians();
    }

    // 🔹 GET by ID
    @GetMapping("/{id}")
    public Physician getPhysicianById(@PathVariable int id) throws Exception {
        return physicianService.getPhysicianById(id);
    }

    // 🔹 POST (create)
    @PostMapping
    public String addPhysician(@RequestBody Physician physician) throws Exception {
        physicianService.addPhysician(physician);
        return "Physician added successfully";
    }

    // 🔹 PUT (update)
    @PutMapping("/{id}")
    public String updatePhysician(@PathVariable int id,
                                  @RequestBody Physician physician) throws Exception {
        physicianService.updatePhysician(id, physician);
        return "Physician updated successfully";
    }

    // 🔹 DELETE
    @DeleteMapping("/{id}")
    public String deletePhysician(@PathVariable int id) throws Exception {
        physicianService.deletePhysician(id);
        return "Physician deleted successfully";
    }
}
