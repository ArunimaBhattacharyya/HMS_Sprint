package com.example.hms.repository;

import com.example.hms.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicationRepository extends JpaRepository<Medication, Integer> {
    List<Medication> findByName(String name);

    List<Medication> findByBrand(String brand);

}
