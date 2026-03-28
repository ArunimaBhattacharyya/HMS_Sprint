package com.example.hms.repository;

import com.example.hms.entity.Stay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StayRepository extends JpaRepository<Stay,Integer> {
    List<Stay> findByPatient_Ssn(int patientId);

    List<Stay> findByRoom_RoomNumber(int roomId);
}
