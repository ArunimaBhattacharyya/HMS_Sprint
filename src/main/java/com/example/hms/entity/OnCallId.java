package com.example.hms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class OnCallId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "Nurse")
    private Nurse nurse;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "BlockFloor", referencedColumnName = "BlockFloor"),
            @JoinColumn(name = "BlockCode", referencedColumnName = "BlockCode")
    })
    private Block block;

    @Column(name = "OnCallStart")
    private LocalDateTime onCallStart;

    public OnCallId() {

    }

    public OnCallId(Nurse nurse, Block block, LocalDateTime onCallStart){
        this.nurse = nurse;
        this.block = block;
        this.onCallStart = onCallStart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OnCallId that)) return false;
        return Objects.equals(nurse, that.nurse) &&
                Objects.equals(block, that.block) &&
                Objects.equals(onCallStart, that.onCallStart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nurse, block, onCallStart);
    }


}
