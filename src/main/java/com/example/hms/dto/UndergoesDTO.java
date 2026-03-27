package com.example.hms.dto;

import java.time.LocalDateTime;

public class UndergoesDTO {

    private int patientId;
    private int procedureId;
    private int stayId;
    private int physicianId;

    private Integer nurseId;

    private LocalDateTime date;

    public UndergoesDTO() {}

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(int procedureId) {
        this.procedureId = procedureId;
    }

    public int getStayId() {
        return stayId;
    }

    public void setStayId(int stayId) {
        this.stayId = stayId;
    }

    public int getPhysicianId() {
        return physicianId;
    }

    public void setPhysicianId(int physicianId) {
        this.physicianId = physicianId;
    }

    public Integer getNurseId() {
        return nurseId;
    }

    public void setNurseId(Integer nurseId) {
        this.nurseId = nurseId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}