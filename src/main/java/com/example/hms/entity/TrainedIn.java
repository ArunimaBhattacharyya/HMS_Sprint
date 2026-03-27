package com.example.hms.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "trained_in")
public class TrainedIn {

    @EmbeddedId
    private TrainedInId id;

    @ManyToOne
    @MapsId("physician")
    @JoinColumn(name = "Physician")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Physician physician;

    @ManyToOne
    @MapsId("treatment")
    @JoinColumn(name = "Treatment")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Procedure procedure;

    @Column(name = "CertificationDate")
    private LocalDateTime certificationDate;

    @Column(name = "CertificationExpires")
    private LocalDateTime certificationExpires;

    public TrainedIn() {}

    public TrainedInId getId() {
        return id;
    }

    public void setId(TrainedInId id) {
        this.id = id;
    }

    public Physician getPhysician() {
        return physician;
    }

    public void setPhysician(Physician physician) {
        this.physician = physician;
    }

    public Procedure getProcedure() {
        return procedure;
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
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