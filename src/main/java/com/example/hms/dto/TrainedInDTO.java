package com.example.hms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class TrainedInDTO {

    private int physicianId;
    private int treatmentId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime certificationDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime certificationExpires;

    public TrainedInDTO() {}

    public int getPhysicianId() {
        return physicianId;
    }

    public void setPhysicianId(int physicianId) {
        this.physicianId = physicianId;
    }

    public int getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(int treatmentId) {
        this.treatmentId = treatmentId;
    }

    public LocalDateTime getCertificationDate() {
        return certificationDate;
    }

    public void setCertificationDate(LocalDateTime certificationDate) {
        this.certificationDate = certificationDate;
    }

    public LocalDateTime getCertificationExpires() {
        return certificationExpires;
    }

    public void setCertificationExpires(LocalDateTime certificationExpires) {
        this.certificationExpires = certificationExpires;
    }
}