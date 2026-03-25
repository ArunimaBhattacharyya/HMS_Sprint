package com.example.hms.repository;

import com.example.hms.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    List<Department> findByName(String name);

    List<Department> findByHead(int head);
}
