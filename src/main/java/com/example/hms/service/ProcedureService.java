package com.example.hms.service;

import com.example.hms.entity.Procedure;
import com.example.hms.repository.ProcedureRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcedureService {

    private final ProcedureRepository procedureRepository;

    public ProcedureService(ProcedureRepository procedureRepository) {
        this.procedureRepository = procedureRepository;
    }

    // Get all procedures
    public List<Procedure> getAllProcedures() {
        return procedureRepository.findAll();
    }

    // Get by ID
    public Procedure getProcedureById(int code) {
        return procedureRepository.findById(code).orElse(null);
    }

    // Save
    public Procedure saveProcedure(Procedure procedure) {
        return procedureRepository.save(procedure);
    }

    // Delete
    public void deleteProcedure(int code) {
        procedureRepository.deleteById(code);
    }
}