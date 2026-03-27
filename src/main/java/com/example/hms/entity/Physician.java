package com.example.hms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "physician")
public class Physician {

    //Getters and Setters
    @Id
    @Column(name = "EmployeeID")
    private int employeeId;

    @Column(name = "Name", nullable = false, length = 30)
    private String name;

    @Column(name = "Position", length = 30)
    private String position;

    @Column(name = "SSN", unique = true)
    private int ssn;

    //One Physician → Many Appointments
    @OneToMany(mappedBy = "physician")
    private List<Appointment> appointments;

//    //One Physician → Many Prescriptions
//    @OneToMany(mappedBy = "physician")
//    private List<Prescribes> prescriptions;

//    //One Physician → Many Undergoes
//    @OneToMany(mappedBy = "physician")
//    private List<Undergoes> undergoes;

//    //Many-to-Many with Department (via affiliated_with)
    @ManyToMany
    @JoinTable(
            name = "affiliated_with",
            joinColumns = @JoinColumn(name = "Physician"),
            inverseJoinColumns = @JoinColumn(name = "Department")
    )
    private List<Department> departments;

//    //Many-to-Many with Procedures (via trained_in)
//    @ManyToMany
//    @JoinTable(
//            name = "trained_in",
//            joinColumns = @JoinColumn(name = "Physician"),
//            inverseJoinColumns = @JoinColumn(name = "Treatment")
//    )
//    private List<Procedures> procedures;

    //Constructors
    public Physician() {

    }

    public Physician(int employeeId, String name, String position, int ssn) {
        this.employeeId = employeeId;
        this.name = name;
        this.position = position;
        this.ssn = ssn;
    }

}