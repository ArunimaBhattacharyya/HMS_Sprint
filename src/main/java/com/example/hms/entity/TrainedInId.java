package com.example.hms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TrainedInId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "Physician")
    private int physician;

    @Column(name = "Treatment")
    private int treatment;

    public TrainedInId() {}

    public TrainedInId(int physician, int treatment) {
        this.physician = physician;
        this.treatment = treatment;
    }

    public int getPhysician() {
        return physician;
    }

    public void setPhysician(int physician) {
        this.physician = physician;
    }

    public int getTreatment() {
        return treatment;
    }

    public void setTreatment(int treatment) {
        this.treatment = treatment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrainedInId)) return false;
        TrainedInId that = (TrainedInId) o;
        return physician == that.physician &&
                treatment == that.treatment;
    }

    @Override
    public int hashCode() {
        return Objects.hash(physician, treatment);
    }
}