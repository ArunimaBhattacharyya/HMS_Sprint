package com.example.hms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "nurse") // must match your MySQL table name exactly
@NoArgsConstructor
@AllArgsConstructor
public class Nurse {

    @Id
    @Column(name = "EmployeeID")
    private int employeeId;

    @Column(name = "Name")
    private String name;

    @Column(name = "Position")
    private String position;

    @Column(name = "Registered")
    private int registered; // keep int if DB uses 0/1

    @Column(name = "SSN")
    private int ssn;


}