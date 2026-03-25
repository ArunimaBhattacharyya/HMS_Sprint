package com.example.hms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.hms.entity.Block;
import com.example.hms.service.BlockService;

@RestController
@RequestMapping("/blocks")
public class BlockController {

    @Autowired
    BlockService blockService;

    //get all
    @GetMapping("/allblock")
    public List<Block> getAllBlock() {
        return blockService.getAllBlock();
    }
    //create
    @PostMapping("/createblock")
    public Block createBlock(@RequestBody Block block) {
        return blockService.saveBlock(block);
    }

    //update
    @PutMapping("/updateblock/{id}")
    public Block updateBlock(@PathVariable int id,@RequestBody Block block){
        block.setBlockfloor(id);
        return blockService.updateBlock(id, block);
    }

    //delete
    @DeleteMapping("/deleteblock/{id}")
    public ResponseEntity<Map<String, String>> deleteBlock(@PathVariable int id) {
        blockService.deleteBlock(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Block deleted successfully");
        return ResponseEntity.ok(response);
    }
}