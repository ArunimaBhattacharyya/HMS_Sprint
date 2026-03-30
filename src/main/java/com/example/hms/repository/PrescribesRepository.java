package com.example.hms.repository;

import com.example.hms.entity.Prescribes;
import com.example.hms.entity.PrescribesId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescribesRepository extends JpaRepository<Prescribes, PrescribesId> {

	List<Prescribes> findByIdPhysician(int physicianId);

	List<Prescribes> findByIdPatient(int patientSsn);

	List<Prescribes> findByIdMedication(int medicationCode);

	List<Prescribes> findByIdPhysicianAndIdPatientAndIdMedication(int physicianId, int patientSsn, int medicationCode);

	List<Prescribes> findByAppointment_AppointmentId(int appointmentId);
}
