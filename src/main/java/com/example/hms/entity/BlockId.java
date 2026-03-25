package com.example.hms.entity;

import java.io.Serializable;
import java.util.Objects;

public class BlockId implements Serializable {

    private int blockFloor;
    private int blockCode;

    public BlockId() {
    }

    public BlockId(int blockFloor, int blockCode) {
        this.blockFloor = blockFloor;
        this.blockCode = blockCode;
    }

    public int getBlockFloor() {
        return blockFloor;
    }

    public void setBlockFloor(int blockFloor) {
        this.blockFloor = blockFloor;
    }

    public int getBlockCode() {
        return blockCode;
    }

    public void setBlockCode(int blockCode) {
        this.blockCode = blockCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BlockId blockId)) {
            return false;
        }
        return blockFloor == blockId.blockFloor && blockCode == blockId.blockCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(blockFloor, blockCode);
    }
}

