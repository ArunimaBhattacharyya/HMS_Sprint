package com.example.hms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "Appointment")
public class Appointment {

	@Id
	@Column(name = "AppointmentID")
	private int appointmentId;

	@Column(name = "Patient", nullable = false)
	private long patientSsn;

	@Column(name = "PrepNurse")
	private Integer prepNurseId;

	@Column(name = "Physician", nullable = false)
	private int physicianId;

	@Column(name = "Starto", nullable = false)
	private LocalDateTime start;

	@Column(name = "Endo", nullable = false)
	private LocalDateTime end;

	@Column(name = "ExaminationRoom", nullable = false)
	private String examinationRoom;

	public int getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}

	public long getPatientSsn() {
		return patientSsn;
	}

	public void setPatientSsn(long patientSsn) {
		this.patientSsn = patientSsn;
	}

	public Integer getPrepNurseId() {
		return prepNurseId;
	}

	public void setPrepNurseId(Integer prepNurseId) {
		this.prepNurseId = prepNurseId;
	}

	public int getPhysicianId() {
		return physicianId;
	}

	public void setPhysicianId(int physicianId) {
		this.physicianId = physicianId;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public String getExaminationRoom() {
		return examinationRoom;
	}

	public void setExaminationRoom(String examinationRoom) {
		this.examinationRoom = examinationRoom;
	}
}
