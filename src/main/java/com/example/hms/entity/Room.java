package com.example.hms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="Room")
public class Room {
    @Id
    @Column(name = "RoomNumber",nullable = false)
    private int roomNumber;

    @Column(name = "RoomType",nullable = false)
    private String roomType;

    @Column(name = "Unavailable",nullable = false)
    private boolean unavailable;

    //many -> one

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "BlockFloor",referencedColumnName = "BlockFloor"),
            @JoinColumn(name = "BlockCode", referencedColumnName = "BlockCode")
    })
    private Block block;
}
