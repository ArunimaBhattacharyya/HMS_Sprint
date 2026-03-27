package com.example.hms.controller;

import com.example.hms.dto.UndergoesDTO;
import com.example.hms.entity.UndergoesId;
import com.example.hms.service.UndergoesService;
import jakarta.validation.Valid;
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
    public UndergoesDTO create(@Valid @RequestBody UndergoesDTO dto) {
        return service.create(dto);
    }

    // UPDATE
    @PutMapping("/{patient}/{procedure}/{stay}/{date}")
    public UndergoesDTO update(
            @PathVariable int patient,
            @PathVariable int procedure,
            @PathVariable int stay,
            @PathVariable String date,
            @Valid @RequestBody UndergoesDTO dto) {

        return service.update(
                patient,
                procedure,
                stay,
                LocalDateTime.parse(date),
                dto
        );
    }

    // DELETE (Better REST style optional below)
    @DeleteMapping("/{patient}/{procedure}/{stay}/{date}")
    public String delete(
            @PathVariable int patient,
            @PathVariable int procedure,
            @PathVariable int stay,
            @PathVariable String date) {

        UndergoesId id = new UndergoesId(
                patient,
                procedure,
                stay,
                LocalDateTime.parse(date)
        );

        service.delete(id);
        return "Deleted successfully";
    }
}