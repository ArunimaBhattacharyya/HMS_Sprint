package com.example.hms.repository;

import com.example.hms.entity.TrainedIn;
import com.example.hms.entity.TrainedInId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainedInRepository extends JpaRepository<TrainedIn, TrainedInId> {

    // Get all records for a specific physician
    List<TrainedIn> findByPhysician_EmployeeId(int employeeId);

    // Get all records for a specific procedure
    List<TrainedIn> findByProcedure_Code(int code);
}