package com.example.hms.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Undergoes")
public class Undergoes {

    @EmbeddedId
    private UndergoesId id;

    @ManyToOne
    @JoinColumn(name = "Patient", insertable = false, updatable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "Procedures", insertable = false, updatable = false)
    private Procedure procedure;

    @ManyToOne
    @JoinColumn(name = "Stay", insertable = false, updatable = false)
    private Stay stay;

    @ManyToOne
    @JoinColumn(name = "Physician")
    private Physician physician;

    @ManyToOne
    @JoinColumn(name = "AssistingNurse")
    private Nurse assistingNurse;

    // ✅ Default Constructor (VERY IMPORTANT for JPA)
    public Undergoes() {}

    // ✅ Parameterized Constructor (optional but useful)
    public Undergoes(UndergoesId id, Patient patient, Procedure procedure,
                     Stay stay, Physician physician, Nurse assistingNurse) {
        this.id = id;
        this.patient = patient;
        this.procedure = procedure;
        this.stay = stay;
        this.physician = physician;
        this.assistingNurse = assistingNurse;
    }

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