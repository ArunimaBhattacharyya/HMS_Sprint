package com.example.hms.repository;

import com.example.hms.entity.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NurseRepository extends JpaRepository<Nurse, Integer> {

    List<Nurse> findByName(String name);

    List<Nurse> findByPosition(String position);
}
