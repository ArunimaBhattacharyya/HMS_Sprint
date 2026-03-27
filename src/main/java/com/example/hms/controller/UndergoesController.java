package com.example.hms.controller;

import com.example.hms.entity.Undergoes;
import com.example.hms.entity.UndergoesId;
import com.example.hms.service.UndergoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/undergoes")
public class UndergoesController {

    @Autowired
    private UndergoesService service;

    @GetMapping
    public List<Undergoes> getAll() {
        return service.getAll();
    }

    //get by patient/procedure/stay/date
    @GetMapping("/{patient}/{procedure}/{stay}/{date}")
    public Undergoes getById(
            @PathVariable int patient,
            @PathVariable int procedure,
            @PathVariable int stay,
            @PathVariable String date) {

        return service.getById(patient, procedure, stay, LocalDateTime.parse(date));
    }

    //create
    @PostMapping
    public Undergoes create(@RequestBody Undergoes u) {
        return service.create(u);
    }

    @DeleteMapping
    public String delete(@RequestBody UndergoesId id) {
        service.delete(id);
        return "Deleted successfully";
    }
}