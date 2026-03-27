package com.example.hms.entity;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Stay")
@Getter
@Setter
public class Stay {

    @Id
    @Column(name="StayId",nullable = false)
    private int stayId;

    // many -> one (Stay -> Patient)
    @ManyToOne
    @JoinColumn(name = "Patient", referencedColumnName = "SSN")
    private Patient patient;

    // many -> one (Stay -> Room)
    @ManyToOne
    @JoinColumn(name = "Room", referencedColumnName = "RoomNumber")
    private Room room;

    @Column(name = "StayStart", nullable = false)
    private LocalDateTime stayStart;

    @Column(name = "StayEnd", nullable = false)
    private LocalDateTime stayEnd;
}
