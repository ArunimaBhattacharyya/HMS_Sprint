package com.example.hms.repository;

import com.example.hms.entity.Undergoes;
import com.example.hms.entity.UndergoesId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UndergoesRepository extends JpaRepository<Undergoes, UndergoesId> {

    List<Undergoes> findByPatient_Ssn(int patientId);

    List<Undergoes> findByProcedure_Code(int procedureId);

    List<Undergoes> findByStay_StayId(int stayId);

    List<Undergoes> findByPhysician_EmployeeId(int physicianId);

    List<Undergoes> findByAssistingNurse_EmployeeId(int nurseId);

    List<Undergoes> findById_DateUndergoesBetween(LocalDateTime start, LocalDateTime end);
}