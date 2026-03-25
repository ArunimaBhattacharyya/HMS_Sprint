package com.example.hms.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "Block")
@IdClass(BlockId.class)
public class Block {

    @Id
    @Column(name = "BlockFloor", nullable = false)
    @JsonProperty("BlockFloor")
    @JsonAlias({"blockFloor", "blockfloor"})
    private int blockFloor;

    @Id
    @Column(name = "BlockCode", nullable = false)
    @JsonProperty("BlockCode")
    @JsonAlias({"blockCode", "blockcode"})
    private int blockCode;

}
