package com.example.hms.service;

import com.example.hms.entity.Block;
import com.example.hms.entity.BlockId;
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
    public Block updateBlock(int blockFloor, int blockCode, Block blockDetails){
        BlockId currentId = new BlockId(blockFloor, blockCode);
        blockRepository.findById(currentId)
                .orElseThrow(() -> new RuntimeException("Block not found: floor=" + blockFloor + ", code=" + blockCode));

        Block replacement = new Block();
        replacement.setBlockFloor(blockDetails.getBlockFloor());
        replacement.setBlockCode(blockDetails.getBlockCode());

        BlockId newId = new BlockId(replacement.getBlockFloor(), replacement.getBlockCode());
        if (!currentId.equals(newId) && blockRepository.existsById(newId)) {
            throw new RuntimeException("Block already exists: floor=" + replacement.getBlockFloor() + ", code=" + replacement.getBlockCode());
        }

        // Composite PK values are the only columns here, so update is replace semantics.
        blockRepository.deleteById(currentId);
        return blockRepository.save(replacement);
    }

    //delete
    public void deleteBlock(int blockFloor, int blockCode){
        BlockId blockId = new BlockId(blockFloor, blockCode);
        Block block = blockRepository.findById(blockId)
                .orElseThrow(() -> new RuntimeException("Block not found: floor=" + blockFloor + ", code=" + blockCode));

        blockRepository.delete(block);
    }

    public List<Block> getBlocksByFloor(int blockFloor) {
        return blockRepository.findByBlockFloor(blockFloor);
    }

    public List<Block> getBlocksByCode(int blockCode) {
        return blockRepository.findByBlockCode(blockCode);
    }

}
