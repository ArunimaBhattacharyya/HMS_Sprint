package com.example.hms.controller;

import com.example.hms.entity.Procedure;
import com.example.hms.service.ProcedureService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/procedures")
public class ProcedureController {

    private final ProcedureService procedureService;

    public ProcedureController(ProcedureService procedureService) {
        this.procedureService = procedureService;
    }

    // GET ALL
    @GetMapping
    public List<Procedure> getAll() {
        return procedureService.getAllProcedures();
    }

    // GET BY ID
    @GetMapping("/{code}")
    public Procedure getById(@PathVariable int code) {
        return procedureService.getProcedureById(code);
    }

    // CREATE
    @PostMapping
    public Procedure create(@RequestBody Procedure procedure) {
        return procedureService.saveProcedure(procedure);
    }

    // UPDATE
    @PutMapping("/{code}")
    public Procedure update(@PathVariable int code, @RequestBody Procedure procedure) {
        return procedureService.updateProcedure(code, procedure);
    }

    // DELETE
    @DeleteMapping("/{code}")
    public String delete(@PathVariable int code) {
        procedureService.deleteProcedure(code);
        return "Procedure deleted successfully";
    }

    // SEARCH BY NAME
    @GetMapping("/search")
    public List<Procedure> searchByName(@RequestParam String name) {
        return procedureService.getByName(name);
    }

    // FILTER BY COST
    @GetMapping("/filterByCost")
    public List<Procedure> filterByCost(
            @RequestParam double min,
            @RequestParam double max) {

        return procedureService.getByCostRange(min, max);
    }
}