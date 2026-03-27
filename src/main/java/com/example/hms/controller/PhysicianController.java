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


    //GET all
    @GetMapping
    public List<Physician> getAllPhysicians() throws Exception {
        return physicianService.getAllPhysicians();
    }

    //GET by ID
    @GetMapping("/{id}")
    public Physician getPhysicianById(@PathVariable int id) throws Exception {
        return physicianService.getPhysicianById(id);
    }

    // GET /physicians/name/{name}
    @GetMapping("/name/{name}")
    public List<Physician> getPhysicianByName(@PathVariable String name) {
        return physicianService.getPhysicianByName(name);
    }

    // GET /physicians/position/{position}
    @GetMapping("/position/{position}")
    public List<Physician> getPhysicianByPosition(@PathVariable String position) {
        return physicianService.getPhysicianByPosition(position);
    }

    //POST (create)
    @PostMapping
    public Physician addPhysician(@RequestBody Physician physician) throws Exception {
        return physicianService.addPhysician(physician);
    }

    //PUT (update)
    @PutMapping("/{id}")
    public Physician updatePhysician(@PathVariable int id,
                                  @RequestBody Physician physician) throws Exception {
        return physicianService.updatePhysician(id, physician);
    }

    //DELETE
    @DeleteMapping("/{id}")
    public String deletePhysician(@PathVariable int id) throws Exception {
        physicianService.deletePhysician(id);
        return "Physician deleted successfully";
    }
}
