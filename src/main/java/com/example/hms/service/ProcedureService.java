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

    // GET ALL
    public List<Procedure> getAllProcedures() {
        return procedureRepository.findAll();
    }

    // GET BY ID
    public Procedure getProcedureById(int code) {
        return procedureRepository.findById(code)
                .orElseThrow(() -> new RuntimeException("Procedure not found with code: " + code));
    }

    // CREATE
    public Procedure saveProcedure(Procedure procedure) {
        return procedureRepository.save(procedure);
    }

    // UPDATE
    public Procedure updateProcedure(int code, Procedure procedure) {

        Procedure existing = getProcedureById(code);

        existing.setName(procedure.getName());
        existing.setCost(procedure.getCost());

        return procedureRepository.save(existing);
    }

    // DELETE
    public void deleteProcedure(int code) {
        if (!procedureRepository.existsById(code)) {
            throw new RuntimeException("Procedure not found with code: " + code);
        }
        procedureRepository.deleteById(code);
    }

    // SEARCH BY NAME
    public List<Procedure> getByName(String name) {
        return procedureRepository.findByNameContainingIgnoreCase(name);
    }

    // FILTER BY COST
    public List<Procedure> getByCostRange(double min, double max) {
        return procedureRepository.findByCostBetween(min, max);
    }
}