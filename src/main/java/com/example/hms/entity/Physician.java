package com.example.hms.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "physician")

@JsonIgnoreProperties({
        "appointments",
        "undergoes",
        "departments",
        "procedures"
})
public class Physician {

    @Id
    @Column(name = "EmployeeID")
    private int employeeId;

    @Column(name = "Name", nullable = false, length = 30)
    private String name;

    @Column(name = "Position", length = 30)
    private String position;

    @Column(name = "SSN", unique = true)
    private int ssn;

    @OneToMany(mappedBy = "physician", fetch = FetchType.LAZY)
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "physician", fetch = FetchType.LAZY)
    private List<Undergoes> undergoes;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "affiliated_with",
            joinColumns = @JoinColumn(name = "Physician"),
            inverseJoinColumns = @JoinColumn(name = "Department")
    )
    private List<Department> departments;

    // Constructors
    public Physician() {}

    public Physician(int employeeId, String name, String position, int ssn) {
        this.employeeId = employeeId;
        this.name = name;
        this.position = position;
        this.ssn = ssn;
    }
}