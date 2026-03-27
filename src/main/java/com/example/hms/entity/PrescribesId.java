package com.example.hms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
public class PrescribesId implements Serializable {

    @Column(name = "Physician")
    private int physician;

    @Column(name = "Patient")
    private int patient;

    @Column(name = "Medication")
    private int medication;

    @Column(name = "`Date`")
    private LocalDateTime date;

    public PrescribesId() {
    }

    public PrescribesId(int physician, int patient, int medication, LocalDateTime date) {
        this.physician = physician;
        this.patient = patient;
        this.medication = medication;
        this.date = date;
    }

    public int getPhysician() {
        return physician;
    }

    public void setPhysician(int physician) {
        this.physician = physician;
    }

    public int getPatient() {
        return patient;
    }

    public void setPatient(int patient) {
        this.patient = patient;
    }

    public int getMedication() {
        return medication;
    }

    public void setMedication(int medication) {
        this.medication = medication;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrescribesId that)) {
            return false;
        }
        return physician == that.physician
                && patient == that.patient
                && medication == that.medication
                && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(physician, patient, medication, date);
    }
}

