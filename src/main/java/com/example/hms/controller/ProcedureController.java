package com.example.hms.controller;

import com.example.hms.entity.Procedure;
import com.example.hms.service.ProcedureService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/procedures")
public class ProcedureController {

    private final ProcedureService procedureService;

    // Constructor Injection (Best Practice 🔥)
    public ProcedureController(ProcedureService procedureService) {
        this.procedureService = procedureService;
    }

    // GET all
    @GetMapping
    public List<Procedure> getAllProcedures() {
        return procedureService.getAllProcedures();
    }

    // GET by ID
    @GetMapping("/{code}")
    public Procedure getProcedure(@PathVariable int code) {
        return procedureService.getProcedureById(code);
    }

    // POST
    @PostMapping
    public Procedure addProcedure(@RequestBody Procedure procedure) {
        return procedureService.saveProcedure(procedure);
    }

    // DELETE
    @DeleteMapping("/{code}")
    public String deleteProcedure(@PathVariable int code) {
        procedureService.deleteProcedure(code);
        return "Procedure deleted successfully";
    }
}