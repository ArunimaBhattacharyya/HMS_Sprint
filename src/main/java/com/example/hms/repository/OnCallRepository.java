package com.example.hms.repository;

import com.example.hms.entity.OnCall;
import com.example.hms.entity.OnCallId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OnCallRepository extends JpaRepository<OnCall, OnCallId> {
    List<OnCall> findById_Nurse_EmployeeId(int nurseId);
}
