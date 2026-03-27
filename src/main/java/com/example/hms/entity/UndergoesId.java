package com.example.hms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
public class UndergoesId implements Serializable {

    @Column(name = "Patient")
    private int patient;

    @Column(name = "Procedures")
    private int procedures;

    @Column(name = "Stay")
    private int stay;

    @Column(name = "DateUndergoes")
    private LocalDateTime dateUndergoes;

    // Default Constructor
    public UndergoesId() {}

    // Parameterized Constructor
    public UndergoesId(int patient, int procedures, int stay, LocalDateTime dateUndergoes) {
        this.patient = patient;
        this.procedures = procedures;
        this.stay = stay;
        this.dateUndergoes = dateUndergoes;
    }

    // ================= GETTERS =================

    public int getPatient() {
        return patient;
    }

    public int getProcedures() {
        return procedures;
    }

    public int getStay() {
        return stay;
    }

    public LocalDateTime getDateUndergoes() {
        return dateUndergoes;
    }

    // ================= SETTERS =================

    public void setPatient(int patient) {
        this.patient = patient;
    }

    public void setProcedures(int procedures) {
        this.procedures = procedures;
    }

    public void setStay(int stay) {
        this.stay = stay;
    }

    public void setDateUndergoes(LocalDateTime dateUndergoes) {
        this.dateUndergoes = dateUndergoes;
    }

    // ================= equals & hashCode =================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UndergoesId)) return false;
        UndergoesId that = (UndergoesId) o;
        return patient == that.patient &&
                procedures == that.procedures &&
                stay == that.stay &&
                Objects.equals(dateUndergoes, that.dateUndergoes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patient, procedures, stay, dateUndergoes);
    }
}