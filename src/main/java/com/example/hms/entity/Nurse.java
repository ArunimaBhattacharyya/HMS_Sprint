package com.example.hms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "Nurse")
public class Nurse {

    @Id
    @Column(name = "EmployeeID")
    private int employeeId;

    @Column(name = "Name")
    private String name;

    @Column(name = "Position")
    private String position;

    @Column(name = "Registered")
    private int registered;

    @Column(name = "SSN")
    private long ssn;
}
