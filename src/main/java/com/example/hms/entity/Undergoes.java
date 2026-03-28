package com.example.hms.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Undergoes")
public class Undergoes {

    @EmbeddedId
    private UndergoesId id;

    @ManyToOne
    @MapsId("patient")
    @JoinColumn(name = "Patient")
    private Patient patient;

    @ManyToOne
    @MapsId("procedures")
    @JoinColumn(name = "Procedures")
    private Procedure procedure;

    @ManyToOne
    @MapsId("stay")
    @JoinColumn(name = "Stay")
    private Stay stay;

    @ManyToOne
    @JoinColumn(name = "Physician")
    private Physician physician;

    @ManyToOne
    @JoinColumn(name = "AssistingNurse")
    private Nurse assistingNurse;

    // ✅ Default Constructor
    public Undergoes() {}

    // ================= GETTERS =================

    public UndergoesId getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public Procedure getProcedure() {
        return procedure;
    }

    public Stay getStay() {
        return stay;
    }

    public Physician getPhysician() {
        return physician;
    }

    public Nurse getAssistingNurse() {
        return assistingNurse;
    }

    // ================= SETTERS =================

    public void setId(UndergoesId id) {
        this.id = id;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
    }

    public void setStay(Stay stay) {
        this.stay = stay;
    }

    public void setPhysician(Physician physician) {
        this.physician = physician;
    }

    public void setAssistingNurse(Nurse assistingNurse) {
        this.assistingNurse = assistingNurse;
    }
}