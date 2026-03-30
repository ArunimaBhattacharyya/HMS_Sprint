package com.example.hms.repository;

import com.example.hms.entity.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcedureRepository extends JpaRepository<Procedure, Integer> {

    // Search by name
    List<Procedure> findByNameContainingIgnoreCase(String name);

    // Cost filters
    List<Procedure> findByCostBetween(double min, double max);
}