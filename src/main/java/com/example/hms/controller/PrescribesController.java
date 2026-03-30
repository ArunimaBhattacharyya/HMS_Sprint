package com.example.hms.controller;

import com.example.hms.dto.PrescribesRequest;
import com.example.hms.dto.PrescribesResponse;
import com.example.hms.service.PrescribesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/prescribes")
public class PrescribesController {

	@Autowired
	private PrescribesService prescribesService;

	@PostMapping
	public PrescribesResponse create(@RequestBody PrescribesRequest request) {
		return prescribesService.create(request);
	}

	@GetMapping
	public List<PrescribesResponse> getAll() {
		return prescribesService.getAll();
	}

	@GetMapping("/{physicianId}/{patientSsn}/{medicationCode}/{date}")
	public PrescribesResponse getById(
			@PathVariable int physicianId,
			@PathVariable int patientSsn,
			@PathVariable int medicationCode,
			@PathVariable String date) {
		return prescribesService.getById(physicianId, patientSsn, medicationCode, LocalDateTime.parse(date));
	}

	@GetMapping("/physician/{physicianId}")
	public List<PrescribesResponse> getByPhysician(@PathVariable int physicianId) {
		return prescribesService.getByPhysician(physicianId);
	}

	@GetMapping("/patient/{patientSsn}")
	public List<PrescribesResponse> getByPatient(@PathVariable int patientSsn) {
		return prescribesService.getByPatient(patientSsn);
	}

	@GetMapping("/medication/{medicationCode}")
	public List<PrescribesResponse> getByMedication(@PathVariable int medicationCode) {
		return prescribesService.getByMedication(medicationCode);
	}

	@GetMapping("/search")
	public List<PrescribesResponse> searchByPhysicianPatientMedication(
			@RequestParam int physicianId,
			@RequestParam int patientSsn,
			@RequestParam int medicationCode) {
		return prescribesService.getByPhysicianPatientMedication(physicianId, patientSsn, medicationCode);
	}

	@GetMapping("/appointment/{appointmentId}")
	public List<PrescribesResponse> getByAppointment(@PathVariable int appointmentId) {
		return prescribesService.getByAppointment(appointmentId);
	}

	@PutMapping("/{physicianId}/{patientSsn}/{medicationCode}/{date}")
	public PrescribesResponse update(
			@PathVariable int physicianId,
			@PathVariable int patientSsn,
			@PathVariable int medicationCode,
			@PathVariable String date,
			@RequestBody PrescribesRequest request) {
		return prescribesService.update(physicianId, patientSsn, medicationCode, LocalDateTime.parse(date), request);
	}

	@DeleteMapping("/{physicianId}/{patientSsn}/{medicationCode}/{date}")
	public String delete(
			@PathVariable int physicianId,
			@PathVariable int patientSsn,
			@PathVariable int medicationCode,
			@PathVariable String date) {
		prescribesService.delete(physicianId, patientSsn, medicationCode, LocalDateTime.parse(date));
		return "Prescribes deleted successfully";
	}
}
