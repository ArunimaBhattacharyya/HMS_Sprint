package com.example.hms.service;

import com.example.hms.dto.PrescribesRequest;
import com.example.hms.dto.PrescribesResponse;
import com.example.hms.entity.Appointment;
import com.example.hms.entity.Medication;
import com.example.hms.entity.Patient;
import com.example.hms.entity.Physician;
import com.example.hms.entity.Prescribes;
import com.example.hms.entity.PrescribesId;
import com.example.hms.repository.AppointmentRepository;
import com.example.hms.repository.MedicationRepository;
import com.example.hms.repository.PatientRepository;
import com.example.hms.repository.PhysicianRepository;
import com.example.hms.repository.PrescribesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PrescribesService {

	@Autowired
	private PrescribesRepository prescribesRepository;

	@Autowired
	private PhysicianRepository physicianRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private MedicationRepository medicationRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	public PrescribesResponse create(PrescribesRequest request) {
		PrescribesId id = buildId(request.getPhysicianId(), request.getPatientSsn(), request.getMedicationCode(), request.getDate());

		Physician physician = physicianRepository.findById(request.getPhysicianId())
				.orElseThrow(() -> new RuntimeException("Physician not found"));

		Patient patient = patientRepository.findById(request.getPatientSsn())
				.orElseThrow(() -> new RuntimeException("Patient not found"));

		Medication medication = medicationRepository.findById(request.getMedicationCode())
				.orElseThrow(() -> new RuntimeException("Medication not found"));

		Appointment appointment = resolveAppointment(request.getAppointmentId());

		Prescribes prescribes = new Prescribes();
		prescribes.setId(id);
		prescribes.setPhysician(physician);
		prescribes.setPatient(patient);
		prescribes.setMedication(medication);
		prescribes.setAppointment(appointment);
		prescribes.setDose(requireDose(request.getDose()));

		return mapToResponse(prescribesRepository.save(prescribes));
	}

	@Transactional(readOnly = true)
	public List<PrescribesResponse> getAll() {
		return prescribesRepository.findAll()
				.stream()
				.map(this::mapToResponse)
				.toList();
	}

	@Transactional(readOnly = true)
	public PrescribesResponse getById(int physicianId, int patientSsn, int medicationCode, LocalDateTime date) {
		PrescribesId id = buildId(physicianId, patientSsn, medicationCode, date);
		Prescribes prescribes = prescribesRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Prescribes record not found"));
		return mapToResponse(prescribes);
	}

	@Transactional(readOnly = true)
	public List<PrescribesResponse> getByPhysician(int physicianId) {
		return prescribesRepository.findByIdPhysician(physicianId)
				.stream()
				.map(this::mapToResponse)
				.toList();
	}

	@Transactional(readOnly = true)
	public List<PrescribesResponse> getByPatient(int patientSsn) {
		return prescribesRepository.findByIdPatient(patientSsn)
				.stream()
				.map(this::mapToResponse)
				.toList();
	}

	@Transactional(readOnly = true)
	public List<PrescribesResponse> getByMedication(int medicationCode) {
		return prescribesRepository.findByIdMedication(medicationCode)
				.stream()
				.map(this::mapToResponse)
				.toList();
	}

	@Transactional(readOnly = true)
	public List<PrescribesResponse> getByPhysicianPatientMedication(int physicianId, int patientSsn, int medicationCode) {
		return prescribesRepository.findByIdPhysicianAndIdPatientAndIdMedication(physicianId, patientSsn, medicationCode)
				.stream()
				.map(this::mapToResponse)
				.toList();
	}

	@Transactional(readOnly = true)
	public List<PrescribesResponse> getByAppointment(int appointmentId) {
		return prescribesRepository.findByAppointment_AppointmentId(appointmentId)
				.stream()
				.map(this::mapToResponse)
				.toList();
	}

	@Transactional
	public PrescribesResponse update(int physicianId, int patientSsn, int medicationCode, LocalDateTime date, PrescribesRequest request) {
		PrescribesId id = buildId(physicianId, patientSsn, medicationCode, date);

		Prescribes existing = prescribesRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Prescribes record not found"));

		int targetMedicationCode = request.getMedicationCode() > 0 ? request.getMedicationCode() : medicationCode;
		Medication targetMedication = medicationRepository.findById(targetMedicationCode)
				.orElseThrow(() -> new RuntimeException("Medication not found"));

		PrescribesId targetId = buildId(physicianId, patientSsn, targetMedicationCode, date);

		if (!targetId.equals(id) && prescribesRepository.existsById(targetId)) {
			throw new RuntimeException("Prescribes record already exists for target medication");
		}

		if (targetId.equals(id)) {
			existing.setMedication(targetMedication);
			existing.setAppointment(resolveAppointment(request.getAppointmentId()));
			existing.setDose(requireDose(request.getDose()));
			return mapToResponse(prescribesRepository.save(existing));
		}

		Prescribes updated = new Prescribes();
		updated.setId(targetId);
		updated.setPhysician(existing.getPhysician());
		updated.setPatient(existing.getPatient());
		updated.setMedication(targetMedication);
		updated.setAppointment(resolveAppointment(request.getAppointmentId()));
		updated.setDose(requireDose(request.getDose()));

		prescribesRepository.delete(existing);
		return mapToResponse(prescribesRepository.save(updated));
	}

	public void delete(int physicianId, int patientSsn, int medicationCode, LocalDateTime date) {
		PrescribesId id = buildId(physicianId, patientSsn, medicationCode, date);
		if (!prescribesRepository.existsById(id)) {
			throw new RuntimeException("Prescribes record not found");
		}
		prescribesRepository.deleteById(id);
	}

	private PrescribesId buildId(int physicianId, int patientSsn, int medicationCode, LocalDateTime date) {
		if (date == null) {
			throw new RuntimeException("Date is required");
		}
		return new PrescribesId(physicianId, patientSsn, medicationCode, date);
	}

	private Appointment resolveAppointment(Integer appointmentId) {
		if (appointmentId == null) {
			return null;
		}
		return appointmentRepository.findById(appointmentId)
				.orElseThrow(() -> new RuntimeException("Appointment not found"));
	}

	private String requireDose(String dose) {
		if (dose == null || dose.isBlank()) {
			throw new RuntimeException("Dose is required");
		}
		return dose;
	}

	private PrescribesResponse mapToResponse(Prescribes prescribes) {
		PrescribesResponse response = new PrescribesResponse();
		response.setPhysicianId(prescribes.getId().getPhysician());
		response.setPhysicianName(prescribes.getPhysician().getName());

		response.setPatientSsn(prescribes.getId().getPatient());
		response.setPatientName(prescribes.getPatient().getName());

		response.setMedicationCode(prescribes.getId().getMedication());
		response.setMedicationName(prescribes.getMedication().getName());

		response.setDate(prescribes.getId().getDate());
		response.setAppointmentId(prescribes.getAppointment() != null ? prescribes.getAppointment().getAppointmentId() : null);
		response.setDose(prescribes.getDose());
		return response;
	}
}
