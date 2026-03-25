package com.example.hms.repository;

import com.example.hms.entity.Physician;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhysicianRepository extends JpaRepository<Physician, Integer> {

}