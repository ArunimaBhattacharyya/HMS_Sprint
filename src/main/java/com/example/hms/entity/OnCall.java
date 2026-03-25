package com.example.hms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "on_call")
public class OnCall {

    @EmbeddedId
    private OnCallId id;

    @Column(name = "OnCallEnd")
    private LocalDateTime onCallEnd;
}
