package com.example.hms.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
public class AffiliatedWithId implements Serializable {

    //Getters and Setters
    private int physician;
    private int department;

    //Default constructor
    public AffiliatedWithId() {

    }

    public AffiliatedWithId(int physician, int department) {
        this.physician = physician;
        this.department = department;
    }

    //equals() and hashCode() are required for composite keys
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AffiliatedWithId that = (AffiliatedWithId) o;
        return physician == that.physician && department == that.department;
    }

    @Override
    public int hashCode() {

        return Objects.hash(physician, department);
    }
}
