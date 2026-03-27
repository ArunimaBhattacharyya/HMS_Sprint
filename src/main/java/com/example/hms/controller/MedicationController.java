package com.example.hms.controller;

import com.example.hms.entity.Medication;
import com.example.hms.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medications")
public class MedicationController {

    @Autowired
    private MedicationService medicationService;

    //get all medications
    @GetMapping
    public List<Medication> getAllMedication() {
        return medicationService.getAllMedication();
    }

    //get medication by id
    @GetMapping("/{code}")
    public Optional<Medication> getMedicationByCode(@PathVariable int code){
        return medicationService.getMedicaltionByCode(code);
    }

    //get medication by name
    @GetMapping("/name/{name}")
    public List<Medication> getMedicationByName(@PathVariable String name){
        return medicationService.getMedicationByName(name);
    }

    //get medication by brand
    @GetMapping("/brand/{brand}")
    public List<Medication> getMedicationByBrand(@PathVariable String brand){
        return medicationService.getMedicationByBrand(brand);
    }

    //create new medication
    @PostMapping
    public Medication createMedication(@RequestBody Medication medication) {
        return medicationService.createMedication(medication);
    }

    //update medication
    @PutMapping("/{code}")
    public Medication updateMedication(@PathVariable int code, @RequestBody Medication medication) {
        return medicationService.updateMedication(code, medication);
    }


}
