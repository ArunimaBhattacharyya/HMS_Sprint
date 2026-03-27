package com.example.hms.controller;

import com.example.hms.dto.UndergoesDTO;
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

    // GET ALL
    @GetMapping
    public List<UndergoesDTO> getAll() {
        return service.getAll();
    }

    // GET BY ID
    @GetMapping("/{patient}/{procedure}/{stay}/{date}")
    public UndergoesDTO getById(
            @PathVariable int patient,
            @PathVariable int procedure,
            @PathVariable int stay,
            @PathVariable String date) {

        return service.getById(
                patient,
                procedure,
                stay,
                LocalDateTime.parse(date)
        );
    }

    // CREATE
    @PostMapping
    public UndergoesDTO create(@RequestBody UndergoesDTO dto) {
        return service.create(dto);
    }

    // DELETE
    @DeleteMapping
    public String delete(@RequestBody UndergoesId id) {
        service.delete(id);
        return "Deleted successfully";
    }
}