package com.example.hms.repository;

import com.example.hms.entity.Undergoes;
import com.example.hms.entity.UndergoesId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UndergoesRepository extends JpaRepository<Undergoes, UndergoesId> {

    List<Undergoes> findByIdPatient(int patient);
    List<Undergoes> findByPhysician_EmployeeId(int physicianId);
    List<Undergoes> findByProcedure_Code(int code);
}