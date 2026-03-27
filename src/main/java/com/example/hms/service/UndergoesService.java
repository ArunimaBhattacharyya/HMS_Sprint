package com.example.hms.service;

import com.example.hms.dto.UndergoesDTO;
import com.example.hms.entity.*;
import com.example.hms.exception.BadRequestException;
import com.example.hms.exception.ResourceNotFoundException;
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

    // ENTITY -> DTO
    private UndergoesDTO convertToDTO(Undergoes u) {

        UndergoesDTO dto = new UndergoesDTO();

        dto.setPatientId(u.getId().getPatient());
        dto.setProcedureId(u.getId().getProcedures());
        dto.setStayId(u.getId().getStay());
        dto.setDate(u.getId().getDateUndergoes());

        dto.setPhysicianId(u.getPhysician().getEmployeeId());

        if (u.getAssistingNurse() != null) {
            dto.setNurseId(u.getAssistingNurse().getEmployeeId());
        }

        return dto;
    }

    // DTO -> ENTITY
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
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found")));

        u.setProcedure(procedureRepo.findById(dto.getProcedureId())
                .orElseThrow(() -> new ResourceNotFoundException("Procedure not found")));

        u.setStay(stayRepo.findById(dto.getStayId())
                .orElseThrow(() -> new ResourceNotFoundException("Stay not found")));

        u.setPhysician(physicianRepo.findById(dto.getPhysicianId())
                .orElseThrow(() -> new ResourceNotFoundException("Physician not found")));

        if (dto.getNurseId() != null) {
            u.setAssistingNurse(
                    nurseRepo.findById(dto.getNurseId())
                            .orElseThrow(() -> new ResourceNotFoundException("Nurse not found"))
            );
        }

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
                .orElseThrow(() -> new ResourceNotFoundException("Record not found"));

        return convertToDTO(u);
    }

    // CREATE
    public UndergoesDTO create(UndergoesDTO dto) {

        int physicianId = dto.getPhysicianId();
        int procedureCode = dto.getProcedureId();

        boolean trained = trainedInRepository
                .findByPhysician_EmployeeId(physicianId)
                .stream()
                .anyMatch(t -> t.getProcedure().getCode() == procedureCode);

        if (!trained) {
            throw new BadRequestException("Physician is NOT trained for this procedure");
        }

        Undergoes u = convertToEntity(dto);

        return convertToDTO(repo.save(u));
    }

    // UPDATE
    public UndergoesDTO update(int patient, int procedure, int stay, LocalDateTime date, UndergoesDTO dto) {

        UndergoesId id = new UndergoesId(patient, procedure, stay, date);

        // check if record exists
        Undergoes existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found"));

        int physicianId = dto.getPhysicianId();
        int procedureCode = dto.getProcedureId();

        // validation: physician must be trained
        boolean trained = trainedInRepository
                .findByPhysician_EmployeeId(physicianId)
                .stream()
                .anyMatch(t -> t.getProcedure().getCode() == procedureCode);

        if (!trained) {
            throw new BadRequestException("Physician is NOT trained for this procedure");
        }

        // update physician
        existing.setPhysician(
                physicianRepo.findById(dto.getPhysicianId())
                        .orElseThrow(() -> new ResourceNotFoundException("Physician not found"))
        );

        // update nurse (nullable)
        if (dto.getNurseId() != null) {
            existing.setAssistingNurse(
                    nurseRepo.findById(dto.getNurseId())
                            .orElseThrow(() -> new ResourceNotFoundException("Nurse not found"))
            );
        } else {
            existing.setAssistingNurse(null);
        }

        return convertToDTO(repo.save(existing));
    }

    // DELETE
    public void delete(UndergoesId id) {

        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Record not found");
        }

        repo.deleteById(id);
    }
}