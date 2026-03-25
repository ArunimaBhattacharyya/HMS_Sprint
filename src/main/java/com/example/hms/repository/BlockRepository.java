package com.example.hms.repository;

import com.example.hms.entity.Block;
import com.example.hms.entity.BlockId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockRepository extends JpaRepository<Block, BlockId> {

}
