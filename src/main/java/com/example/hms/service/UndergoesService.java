package com.example.hms.service;

import com.example.hms.dto.UndergoesDTO;
import com.example.hms.entity.*;
import com.example.hms.exception.ResourceNotFoundException;
import com.example.hms.repository.UndergoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UndergoesService {

    @Autowired
    private UndergoesRepository repository;

    public List<UndergoesDTO> getAll() {
        return repository.findAll()
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public UndergoesDTO getById(int patient, int procedure, int stay, LocalDateTime date) {
        UndergoesId id = new UndergoesId(patient, procedure, stay, date);

        Undergoes entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Undergoes not found"));

        return convertToDTO(entity);
    }

    public List<UndergoesDTO> getByPatient(int patientId) {
        return repository.findByPatient_Ssn(patientId)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<UndergoesDTO> getByProcedure(int procedureId) {
        return repository.findByProcedure_Code(procedureId)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }


    public List<UndergoesDTO> getByStay(int stayId) {
        return repository.findByStay_StayId(stayId)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<UndergoesDTO> getByPhysician(int physicianId) {
        return repository.findByPhysician_EmployeeId(physicianId)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<UndergoesDTO> getByNurse(int nurseId) {
        return repository.findByAssistingNurse_EmployeeId(nurseId)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<UndergoesDTO> getByDateRange(LocalDateTime start, LocalDateTime end) {
        return repository.findById_DateUndergoesBetween(start, end)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public UndergoesDTO create(UndergoesDTO dto) {
        return convertToDTO(repository.save(convertToEntity(dto)));
    }

    public UndergoesDTO update(int patient, int procedure, int stay, LocalDateTime date, UndergoesDTO dto) {

        UndergoesId id = new UndergoesId(patient, procedure, stay, date);

        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Undergoes not found"));

        Undergoes updated = convertToEntity(dto);
        updated.setId(id);

        return convertToDTO(repository.save(updated));
    }

    public void delete(UndergoesId id) {
        repository.deleteById(id);
    }

    // ================== CONVERSIONS ==================

    private UndergoesDTO convertToDTO(Undergoes u) {
        UndergoesDTO dto = new UndergoesDTO();

        dto.setPatient(u.getPatient().getSsn());
        dto.setProcedure(u.getProcedure().getCode());
        dto.setStay(u.getStay().getStayId());
        dto.setDateUndergoes(u.getId().getDateUndergoes());
        dto.setPhysician(u.getPhysician().getEmployeeId());

        if (u.getAssistingNurse() != null) {
            dto.setAssistingNurse(u.getAssistingNurse().getEmployeeId());
        }

        return dto;
    }

    private Undergoes convertToEntity(UndergoesDTO dto) {

        Undergoes u = new Undergoes();

        UndergoesId id = new UndergoesId(
                dto.getPatient(),
                dto.getProcedure(),
                dto.getStay(),
                dto.getDateUndergoes()
        );

        u.setId(id);

        Patient p = new Patient();
        p.setSsn(dto.getPatient());

        Procedure proc = new Procedure();
        proc.setCode(dto.getProcedure());

        Stay s = new Stay();
        s.setStayId(dto.getStay());

        Physician ph = new Physician();
        ph.setEmployeeId(dto.getPhysician());

        u.setPatient(p);
        u.setProcedure(proc);
        u.setStay(s);
        u.setPhysician(ph);

        if (dto.getAssistingNurse() != null) {
            Nurse n = new Nurse();
            n.setEmployeeId(dto.getAssistingNurse());
            u.setAssistingNurse(n);
        }

        return u;
    }
}