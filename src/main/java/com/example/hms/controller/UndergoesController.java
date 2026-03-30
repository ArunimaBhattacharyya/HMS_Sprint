package com.example.hms.controller;

import com.example.hms.service.UndergoesService;
import com.example.hms.dto.UndergoesDTO;
import com.example.hms.entity.UndergoesId;
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

    // GET BY COMPOSITE ID
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

    // FILTER APIs
    @GetMapping("/patient/{patientId}")
    public List<UndergoesDTO> getByPatient(@PathVariable int patientId) {
        return service.getByPatient(patientId);
    }

    @GetMapping("/procedure/{procedureId}")
    public List<UndergoesDTO> getByProcedure(@PathVariable int procedureId) {
        return service.getByProcedure(procedureId);
    }

    @GetMapping("/stay/{stayId}")
    public List<UndergoesDTO> getByStay(@PathVariable int stayId) {
        return service.getByStay(stayId);
    }

    @GetMapping("/physician/{physicianId}")
    public List<UndergoesDTO> getByPhysician(@PathVariable int physicianId) {
        return service.getByPhysician(physicianId);
    }

    @GetMapping("/nurse/{nurseId}")
    public List<UndergoesDTO> getByNurse(@PathVariable int nurseId) {
        return service.getByNurse(nurseId);
    }

    @GetMapping("/date-range")
    public List<UndergoesDTO> getByDateRange(
            @RequestParam String start,
            @RequestParam String end) {

        return service.getByDateRange(
                LocalDateTime.parse(start),
                LocalDateTime.parse(end)
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

    // DELETE
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