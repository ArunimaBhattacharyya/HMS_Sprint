package com.example.hms.repository;

import com.example.hms.entity.Physician;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhysicianRepository extends JpaRepository<Physician, Integer> {

    List<Physician> findByName(String name);

    List<Physician> findByPosition(String position);
}