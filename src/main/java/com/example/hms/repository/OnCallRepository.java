package com.example.hms.repository;

import com.example.hms.entity.Block;
import com.example.hms.entity.OnCall;
import com.example.hms.entity.OnCallId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OnCallRepository extends JpaRepository<OnCall, OnCallId> {
    // by nurse
    List<OnCall> findById_Nurse_EmployeeId(int nurseId);

    // by block floor
    List<OnCall> findById_Block_BlockFloor(int blockFloor);

    // by block code
    List<OnCall> findById_Block_BlockCode(int blockCode);
}
