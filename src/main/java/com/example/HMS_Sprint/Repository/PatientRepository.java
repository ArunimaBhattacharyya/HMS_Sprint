package com.example.HMS_Sprint.Repository;

import com.example.HMS_Sprint.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findByName(String name);

    List<Patient> findByPcp(Integer pcp);
}
