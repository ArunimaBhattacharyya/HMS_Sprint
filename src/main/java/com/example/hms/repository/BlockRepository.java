package com.example.hms.repository;

import com.example.hms.entity.Block;
import com.example.hms.entity.BlockId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockRepository extends JpaRepository<Block, BlockId> {

    List<Block> findByBlockFloor(int blockFloor);

    List<Block> findByBlockCode(int blockCode);
}
