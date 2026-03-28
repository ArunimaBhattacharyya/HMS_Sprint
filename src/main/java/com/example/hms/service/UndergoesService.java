package com.example.hms.service;

import com.example.hms.dto.UndergoesDTO;
import com.example.hms.entity.UndergoesId;

import java.time.LocalDateTime;
import java.util.List;

public interface UndergoesService {

    List<UndergoesDTO> getAll();

    UndergoesDTO getById(int patient, int procedure, int stay, LocalDateTime date);

    List<UndergoesDTO> getByPatient(int patientId);

    List<UndergoesDTO> getByProcedure(int procedureId);

    List<UndergoesDTO> getByStay(int stayId);

    List<UndergoesDTO> getByPhysician(int physicianId);

    List<UndergoesDTO> getByNurse(int nurseId);

    List<UndergoesDTO> getByDateRange(LocalDateTime start, LocalDateTime end);

    UndergoesDTO create(UndergoesDTO dto);

    UndergoesDTO update(int patient, int procedure, int stay, LocalDateTime date, UndergoesDTO dto);

    void delete(UndergoesId id);
}