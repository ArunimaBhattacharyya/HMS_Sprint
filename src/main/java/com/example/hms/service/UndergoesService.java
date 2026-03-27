package com.example.hms.service;

import com.example.hms.dto.UndergoesDTO;
import com.example.hms.entity.*;
import com.example.hms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UndergoesService {

    @Autowired
    private UndergoesRepository repo;

    @Autowired
    private TrainedInRepository trainedInRepository;

    @Autowired private PatientRepository patientRepo;
    @Autowired private ProcedureRepository procedureRepo;
    @Autowired private StayRepository stayRepo;
    @Autowired private PhysicianRepository physicianRepo;
    @Autowired private NurseRepository nurseRepo;

    // ENTITY → DTO
    private UndergoesDTO convertToDTO(Undergoes u) {

        UndergoesDTO dto = new UndergoesDTO();

        dto.setPatientId(u.getId().getPatient());
        dto.setProcedureId(u.getId().getProcedures());
        dto.setStayId(u.getId().getStay());
        dto.setDate(u.getId().getDateUndergoes());

        dto.setPhysicianId(u.getPhysician().getEmployeeId());
        dto.setNurseId(u.getAssistingNurse().getEmployeeId());

        return dto;
    }

    // DTO → ENTITY
    private Undergoes convertToEntity(UndergoesDTO dto) {

        Undergoes u = new Undergoes();

        UndergoesId varOcg = new UndergoesId(
                dto.getPatientId(),
                dto.getProcedureId(),
                dto.getStayId(),
                dto.getDate()
        );

        u.setId(varOcg);

        u.setPatient(patientRepo.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found")));

        u.setProcedure(procedureRepo.findById(dto.getProcedureId())
                .orElseThrow(() -> new RuntimeException("Procedure not found")));

        u.setStay(stayRepo.findById(dto.getStayId())
                .orElseThrow(() -> new RuntimeException("Stay not found")));

        u.setPhysician(physicianRepo.findById(dto.getPhysicianId())
                .orElseThrow(() -> new RuntimeException("Physician not found")));

        u.setAssistingNurse(nurseRepo.findById(dto.getNurseId())
                .orElseThrow(() -> new RuntimeException("Nurse not found")));

        return u;
    }

    // GET ALL
    public List<UndergoesDTO> getAll() {
        return repo.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // GET BY ID
    public UndergoesDTO getById(int patient, int procedure, int stay, LocalDateTime date) {

        UndergoesId id = new UndergoesId(patient, procedure, stay, date);

        Undergoes u = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        return convertToDTO(u);
    }

    // CREATE
    public UndergoesDTO create(UndergoesDTO dto) {

        Undergoes u = convertToEntity(dto);

        int physicianId = dto.getPhysicianId();
        int procedureCode = dto.getProcedureId();

        boolean trained = trainedInRepository
                .findByPhysician_EmployeeId(physicianId)
                .stream()
                .anyMatch(t -> t.getProcedure().getCode() == procedureCode);

        if (!trained) {
            throw new RuntimeException("Physician is NOT trained for this procedure");
        }

        return convertToDTO(repo.save(u));
    }

    // DELETE
    public void delete(UndergoesId id) {
        repo.deleteById(id);
    }
}