package com.example.hms.service;

import com.example.hms.dto.TrainedInDTO;
import com.example.hms.entity.*;
import com.example.hms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainedInService {

    @Autowired
    private TrainedInRepository repo;

    @Autowired
    private PhysicianRepository physicianRepo;

    @Autowired
    private ProcedureRepository procedureRepo;

    // __define-ocg__ mapping inside service

    private TrainedInDTO convertToDTO(TrainedIn entity) {
        TrainedInDTO dto = new TrainedInDTO();

        dto.setPhysicianId(entity.getId().getPhysician());
        dto.setTreatmentId(entity.getId().getTreatment());
        dto.setCertificationDate(entity.getCertificationDate());
        dto.setCertificationExpires(entity.getCertificationExpires());

        return dto;
    }

    private TrainedIn convertToEntity(TrainedInDTO dto, Physician physician, Procedure procedure) {

        TrainedIn entity = new TrainedIn();

        TrainedInId varOcg = new TrainedInId(
                dto.getPhysicianId(),
                dto.getTreatmentId()
        );

        entity.setId(varOcg);
        entity.setPhysician(physician);
        entity.setProcedure(procedure);
        entity.setCertificationDate(dto.getCertificationDate());
        entity.setCertificationExpires(dto.getCertificationExpires());

        return entity;
    }

    public List<TrainedInDTO> getAll() {
        return repo.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TrainedInDTO save(TrainedInDTO dto) {

        TrainedInId id = new TrainedInId(dto.getPhysicianId(), dto.getTreatmentId());

        if (repo.existsById(id)) {
            throw new RuntimeException("Already exists");
        }

        Physician physician = physicianRepo.findById(dto.getPhysicianId())
                .orElseThrow(() -> new RuntimeException("Physician not found"));

        Procedure procedure = procedureRepo.findById(dto.getTreatmentId())
                .orElseThrow(() -> new RuntimeException("Procedure not found"));

        TrainedIn entity = convertToEntity(dto, physician, procedure);

        return convertToDTO(repo.save(entity));
    }

    public TrainedInDTO getById(int physician, int treatment) {

        TrainedIn entity = repo.findById(new TrainedInId(physician, treatment))
                .orElseThrow(() -> new RuntimeException("Not found"));

        return convertToDTO(entity);
    }

    public List<TrainedInDTO> getByPhysician(int id) {
        return repo.findByPhysician_EmployeeId(id)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TrainedInDTO> getByProcedure(int code) {
        return repo.findByProcedure_Code(code)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TrainedInDTO update(int physician, int treatment, TrainedInDTO dto) {

        TrainedIn existing = repo.findById(new TrainedInId(physician, treatment))
                .orElseThrow(() -> new RuntimeException("Not found"));

        existing.setCertificationDate(dto.getCertificationDate());
        existing.setCertificationExpires(dto.getCertificationExpires());

        return convertToDTO(repo.save(existing));
    }

    public void delete(int physician, int treatment) {

        TrainedInId id = new TrainedInId(physician, treatment);

        if (!repo.existsById(id)) {
            throw new RuntimeException("Not found");
        }

        repo.deleteById(id);
    }

    public boolean exists(int physician, int treatment) {
        return repo.existsById(new TrainedInId(physician, treatment));
    }
}