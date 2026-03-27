package com.example.hms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "Appointment")
public class Appointment {

	@Id
	@Column(name = "AppointmentID")
	private int appointmentId;

	@Column(name = "Patient", nullable = false)
	private int patientSsn;

	@ManyToOne
	@JoinColumn(name = "Patient", referencedColumnName = "SSN", insertable = false, updatable = false)
	private Patient patient;

	@Column(name = "PrepNurse")
	private Integer prepNurseId;

	@ManyToOne
	@JoinColumn(name = "PrepNurse", referencedColumnName = "EmployeeID", insertable = false, updatable = false)
	private Nurse prepNurse;

	@Column(name = "Physician", nullable = false)
	private int physicianId;

	@ManyToOne
	@JoinColumn(name = "Physician", referencedColumnName = "EmployeeID", insertable = false, updatable = false)
	private Physician physician;

	@Column(name = "Starto", nullable = false)
	private LocalDateTime starto;

	@Column(name = "Endo", nullable = false)
	private LocalDateTime endo;

	@Column(name = "ExaminationRoom", nullable = false)
	private String examinationRoom;

	public int getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}

	public int getPatientSsn() {
		return patientSsn;
	}

	public void setPatientSsn(int patientSsn) {
		this.patientSsn = patientSsn;
	}

	public Integer getPrepNurseId() {
		return prepNurseId;
	}

	public void setPrepNurseId(Integer prepNurseId) {
		this.prepNurseId = prepNurseId;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Nurse getPrepNurse() {
		return prepNurse;
	}

	public void setPrepNurse(Nurse prepNurse) {
		this.prepNurse = prepNurse;
	}

	public Physician getPhysician() {
		return physician;
	}

	public void setPhysician(Physician physician) {
		this.physician = physician;
	}

	public int getPhysicianId() {
		return physicianId;
	}

	public void setPhysicianId(int physicianId) {
		this.physicianId = physicianId;
	}

	public LocalDateTime getStart() {
		return starto;
	}

	public void setStart(LocalDateTime start) {
		this.starto = start;
	}

	public LocalDateTime getEnd() {
		return endo;
	}

	public void setEnd(LocalDateTime end) {
		this.endo = end;
	}

	public String getExaminationRoom() {
		return examinationRoom;
	}

	public void setExaminationRoom(String examinationRoom) {
		this.examinationRoom = examinationRoom;
	}
}
