package com.example.hms.service;

import com.example.hms.entity.*;
import com.example.hms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UndergoesService {

    @Autowired
    private UndergoesRepository repo;

    @Autowired
    private TrainedInRepository trainedInRepository;

    public List<Undergoes> getAll() {
        return repo.findAll();
    }

    public Undergoes getById(int patient, int procedure, int stay, LocalDateTime date) {
        UndergoesId id = new UndergoesId(patient, procedure, stay, date);
        return repo.findById(id).orElse(null);
    }

    // LOGIC
    public Undergoes create(Undergoes u) {

        int physicianId = u.getPhysician().getEmployeeId();
        int procedureCode = u.getProcedure().getCode();

        // VALIDATION
        boolean trained = trainedInRepository
                .findByPhysician_EmployeeId(physicianId)
                .stream()
                .anyMatch(t -> t.getProcedure().getCode() == procedureCode);

        if (!trained) {
            throw new RuntimeException("Physician is NOT trained for this procedure");
        }

        return repo.save(u);
    }

    public void delete(UndergoesId id) {
        repo.deleteById(id);
    }
}