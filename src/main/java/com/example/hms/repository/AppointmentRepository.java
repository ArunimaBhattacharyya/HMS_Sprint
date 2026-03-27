package com.example.hms.repository;

import com.example.hms.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

	List<Appointment> findByPatientSsn(int patientSsn);

	List<Appointment> findByPrepNurseId(int prepNurseId);

	List<Appointment> findByPhysicianId(int physicianId);
}
