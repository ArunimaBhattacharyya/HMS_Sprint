package com.example.hms.controller;

import com.example.hms.dto.AppointmentRequest;
import com.example.hms.dto.AppointmentResponse;
import com.example.hms.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;

	@PostMapping
	public AppointmentResponse create(@RequestBody AppointmentRequest request) {
		return appointmentService.create(request);
	}

	@GetMapping
	public List<AppointmentResponse> getAll() {
		return appointmentService.getAll();
	}

	@GetMapping("/{id}")
	public AppointmentResponse getById(@PathVariable int id) {
		return appointmentService.getById(id);
	}

	@GetMapping("/patient/{patientSsn}")
	public List<AppointmentResponse> getByPatient(@PathVariable int patientSsn) {
		return appointmentService.getByPatient(patientSsn);
	}

	@GetMapping("/nurse/{nurseId}")
	public List<AppointmentResponse> getByNurse(@PathVariable int nurseId) {
		return appointmentService.getByNurse(nurseId);
	}

	@GetMapping("/physician/{physicianId}")
	public List<AppointmentResponse> getByPhysician(@PathVariable int physicianId) {
		return appointmentService.getByPhysician(physicianId);
	}

	@PutMapping("/{id}")
	public AppointmentResponse update(@PathVariable int id, @RequestBody AppointmentRequest request) {
		return appointmentService.update(id, request);
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable int id) {
		appointmentService.delete(id);
		return "Appointment deleted successfully";
	}
}
