package com.example.hms.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "procedures")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Procedure {

    @Id
    private int code;

    private String name;

    private double cost;
}