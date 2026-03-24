package com.example.HMS_Sprint.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="Block")

public class Block {

    @Id
    @Column(name="BlockFloor")
    private int blockfloor;

    @Column(name="BlockCode")
    private int blockcode;

}
