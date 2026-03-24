package com.example.HMS_Sprint.Repository;

import com.example.HMS_Sprint.Entity.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NurseRepository extends JpaRepository<Nurse, Integer> {

    List<Nurse> findByName(String name);

    List<Nurse> findByPosition(String position);
}
