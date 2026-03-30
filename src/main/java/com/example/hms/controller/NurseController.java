package com.example.hms.controller;

import com.example.hms.entity.Nurse;
import com.example.hms.service.NurseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/nurses")
public class NurseController {

    @Autowired
    private NurseService nurseServices;

    // GET /nurses
    @GetMapping
    public List<Nurse> getAllNurses() {
        return nurseServices.getAllNurses();
    }

    // GET /nurses/{id}
    @GetMapping("/{id}")
    public Nurse getNurseById(@PathVariable int id) {
        Optional<Nurse> nurse = nurseServices.getNurseById(id);
        return nurse.orElse(null);
    }

    // GET /nurses/name/{name}
    @GetMapping("/name/{name}")
    public List<Nurse> getNurseByName(@PathVariable String name) {
        return nurseServices.getNurseByName(name);
    }

    // GET /nurses/position/{position}
    @GetMapping("/position/{position}")
    public List<Nurse> getNurseByPosition(@PathVariable String position) {
        return nurseServices.getNurseByPosition(position);
    }

    // POST /nurses
    @PostMapping
    public Nurse createNurse(@RequestBody Nurse nurse) {
        return nurseServices.createNurse(nurse);
    }

    // PUT /nurses/{id}
    @PutMapping("/{id}")
    public Nurse updateNurse(@PathVariable int id, @RequestBody Nurse nurse) {
        return nurseServices.updateNurse(id, nurse);
    }

//    // DELETE /nurses/{id}
//    @DeleteMapping("/{id}")
//    public String deleteNurse(@PathVariable int id) {
//        nurseServices.deleteNurse(id);
//        return "Nurse deleted successfully";
//    }
}