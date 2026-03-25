package com.example.hms.service;

import com.example.hms.entity.Block;
import com.example.hms.repository.BlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockService {
    @Autowired
    private BlockRepository blockRepository;

    //create
    public Block saveBlock(Block block){
        return blockRepository.save(block);
    }

    //read all
    public List<Block> getAllBlock(){
        return blockRepository.findAll();
    }

    //update
    public Block updateBlock(int id,Block blockdetails){
        Block block=blockRepository.findById(id).orElseThrow(()->new RuntimeException("Block not found"+id));

        //update fields example
        block.setBlockfloor(blockdetails.getBlockfloor());
        block.setBlockcode(blockdetails.getBlockcode());

        return blockRepository.save(block);
    }

    //delete
    public void deleteBlock(int id){
        Block block=blockRepository.findById(id).orElseThrow(()->new RuntimeException("Block not found with id "+id));

        blockRepository.delete(block);
    }

}
