package com.example.hms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "affiliated_with")
@IdClass(AffiliatedWithId.class) // Tells JPA to use the composite key class
public class AffiliatedWith {

    //Getters and Setters (Handled by Lombok)

    @Id
    @Column(name = "Physician")
    private int physician;

    @Id
    @Column(name = "Department")
    private int department;

    @Column(name = "PrimaryAffiliation")
    private Boolean primaryAffiliation; // BIT(1) maps to Boolean in Java

    //Commented relationships for future use

    //Many-to-One with Physician
    @ManyToOne
    @JoinColumn(name = "Physician", insertable = false, updatable = false)
    private Physician physicianEntity;

    //Many-to-One with Department
    @ManyToOne
    @JoinColumn(name = "Department", insertable = false, updatable = false)
    private Department departmentEntity;

    //Constructors
    public AffiliatedWith() {
    }

    public AffiliatedWith(int physician, int department, Boolean primaryAffiliation) {
        this.physician = physician;
        this.department = department;
        this.primaryAffiliation = primaryAffiliation;
    }
}
