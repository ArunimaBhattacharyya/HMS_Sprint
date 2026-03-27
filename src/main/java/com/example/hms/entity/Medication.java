package com.example.hms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "medication")
public class Medication {

    @Id
    @Column(name = "Code")
    private int code;

    @Column(name = "Name")
    private String name;

    @Column(name = "Brand")
    private String brand;

    @Column(name = "Description")
    private String description;

}
