package com.example.hms.service;

import com.example.hms.dto.AppointmentRequest;
import com.example.hms.dto.AppointmentResponse;
import com.example.hms.entity.Appointment;
import com.example.hms.entity.Nurse;
import com.example.hms.entity.Patient;
import com.example.hms.entity.Physician;
import com.example.hms.repository.AppointmentRepository;
import com.example.hms.repository.NurseRepository;
import com.example.hms.repository.PatientRepository;
import com.example.hms.repository.PhysicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private NurseRepository nurseRepository;

	@Autowired
	private PhysicianRepository physicianRepository;

	public AppointmentResponse create(AppointmentRequest request) {
		Patient patient = patientRepository.findById(request.getPatientSsn())
				.orElseThrow(() -> new RuntimeException("Patient not found"));

		Physician physician = physicianRepository.findById(request.getPhysicianId())
				.orElseThrow(() -> new RuntimeException("Physician not found"));

		Nurse prepNurse = resolveNurseIfPresent(request.getPrepNurseId());

		validateTimeWindow(request.getStart(), request.getEnd());

		Appointment appointment = new Appointment();
		appointment.setAppointmentId(request.getAppointmentId());
		appointment.setPatientSsn(patient.getSsn());
		appointment.setPatient(patient);
		appointment.setPrepNurseId(request.getPrepNurseId());
		appointment.setPrepNurse(prepNurse);
		appointment.setPhysicianId(physician.getEmployeeId());
		appointment.setPhysician(physician);
		appointment.setStart(request.getStart());
		appointment.setEnd(request.getEnd());
		appointment.setExaminationRoom(request.getExaminationRoom());

		Appointment saved = appointmentRepository.save(appointment);
		return mapToResponse(saved, patient, prepNurse, physician);
	}

	public List<AppointmentResponse> getAll() {
		return appointmentRepository.findAll()
				.stream()
				.map(this::mapToResponse)
				.toList();
	}

	public AppointmentResponse getById(int appointmentId) {
		Appointment appointment = appointmentRepository.findById(appointmentId)
				.orElseThrow(() -> new RuntimeException("Appointment not found"));
		return mapToResponse(appointment);
	}

	public List<AppointmentResponse> getByPatient(int patientSsn) {
		return appointmentRepository.findByPatientSsn(patientSsn)
				.stream()
				.map(this::mapToResponse)
				.toList();
	}

	public List<AppointmentResponse> getByNurse(int prepNurseId) {
		return appointmentRepository.findByPrepNurseId(prepNurseId)
				.stream()
				.map(this::mapToResponse)
				.toList();
	}

	public List<AppointmentResponse> getByPhysician(int physicianId) {
		return appointmentRepository.findByPhysicianId(physicianId)
				.stream()
				.map(this::mapToResponse)
				.toList();
	}

	public AppointmentResponse update(int appointmentId, AppointmentRequest request) {
		Appointment existing = appointmentRepository.findById(appointmentId)
				.orElseThrow(() -> new RuntimeException("Appointment not found"));

		Patient patient = patientRepository.findById(request.getPatientSsn())
				.orElseThrow(() -> new RuntimeException("Patient not found"));

		Physician physician = physicianRepository.findById(request.getPhysicianId())
				.orElseThrow(() -> new RuntimeException("Physician not found"));

		Nurse prepNurse = resolveNurseIfPresent(request.getPrepNurseId());

		validateTimeWindow(request.getStart(), request.getEnd());

		existing.setPatientSsn(patient.getSsn());
		existing.setPatient(patient);
		existing.setPrepNurseId(request.getPrepNurseId());
		existing.setPrepNurse(prepNurse);
		existing.setPhysicianId(physician.getEmployeeId());
		existing.setPhysician(physician);
		existing.setStart(request.getStart());
		existing.setEnd(request.getEnd());
		existing.setExaminationRoom(request.getExaminationRoom());

		Appointment saved = appointmentRepository.save(existing);
		return mapToResponse(saved, patient, prepNurse, physician);
	}

	public void delete(int appointmentId) {
		if (!appointmentRepository.existsById(appointmentId)) {
			throw new RuntimeException("Appointment not found");
		}
		appointmentRepository.deleteById(appointmentId);
	}

	private Nurse resolveNurseIfPresent(Integer prepNurseId) {
		if (prepNurseId == null) {
			return null;
		}
		return nurseRepository.findById(prepNurseId)
				.orElseThrow(() -> new RuntimeException("Nurse not found"));
	}

	private void validateTimeWindow(java.time.LocalDateTime start, java.time.LocalDateTime end) {
		if (start == null || end == null || !end.isAfter(start)) {
			throw new RuntimeException("Invalid appointment time window");
		}
	}

	private AppointmentResponse mapToResponse(Appointment appointment) {
		Patient patient = appointment.getPatient() != null
				? appointment.getPatient()
				: patientRepository.findById(appointment.getPatientSsn())
						.orElseThrow(() -> new RuntimeException("Patient not found for appointment"));

		Physician physician = appointment.getPhysician() != null
				? appointment.getPhysician()
				: physicianRepository.findById(appointment.getPhysicianId())
						.orElseThrow(() -> new RuntimeException("Physician not found for appointment"));

		Nurse prepNurse = appointment.getPrepNurse();
		if (prepNurse == null && appointment.getPrepNurseId() != null) {
			prepNurse = nurseRepository.findById(appointment.getPrepNurseId())
					.orElseThrow(() -> new RuntimeException("Nurse not found for appointment"));
		}

		return mapToResponse(appointment, patient, prepNurse, physician);
	}

	private AppointmentResponse mapToResponse(Appointment appointment, Patient patient, Nurse prepNurse, Physician physician) {
		AppointmentResponse response = new AppointmentResponse();
		response.setAppointmentId(appointment.getAppointmentId());

		response.setPatientSsn(patient.getSsn());
		response.setPatientName(patient.getName());

		response.setPrepNurseId(appointment.getPrepNurseId());
		response.setPrepNurseName(prepNurse != null ? prepNurse.getName() : null);

		response.setPhysicianId(physician.getEmployeeId());
		response.setPhysicianName(physician.getName());

		response.setStart(appointment.getStart());
		response.setEnd(appointment.getEnd());
		response.setExaminationRoom(appointment.getExaminationRoom());
		return response;
	}
}
