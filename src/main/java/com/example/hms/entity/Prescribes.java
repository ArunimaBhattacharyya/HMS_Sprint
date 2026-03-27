package com.example.hms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "Prescribes")
public class Prescribes {

	@EmbeddedId
	private PrescribesId id;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("physician")
	@JoinColumn(name = "Physician", referencedColumnName = "EmployeeID")
	private Physician physician;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("patient")
	@JoinColumn(name = "Patient", referencedColumnName = "SSN")
	private Patient patient;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("medication")
	@JoinColumn(name = "Medication", referencedColumnName = "Code")
	private Medication medication;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Appointment", referencedColumnName = "AppointmentID")
	private Appointment appointment;

	@Column(name = "Dose", nullable = false, length = 30)
	private String dose;

	public PrescribesId getId() {
		return id;
	}

	public void setId(PrescribesId id) {
		this.id = id;
	}

	public Physician getPhysician() {
		return physician;
	}

	public void setPhysician(Physician physician) {
		this.physician = physician;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Medication getMedication() {
		return medication;
	}

	public void setMedication(Medication medication) {
		this.medication = medication;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public String getDose() {
		return dose;
	}

	public void setDose(String dose) {
		this.dose = dose;
	}
}
