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
    @PutMapping("/updateblock/{blockFloor}/{blockCode}")
    public Block updateBlock(@PathVariable int blockFloor, @PathVariable int blockCode, @RequestBody Block block){
        return blockService.updateBlock(blockFloor, blockCode, block);
    }

    //delete
    @DeleteMapping("/deleteblock/{blockFloor}/{blockCode}")
    public ResponseEntity<Map<String, String>> deleteBlock(@PathVariable int blockFloor, @PathVariable int blockCode) {
        blockService.deleteBlock(blockFloor, blockCode);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Block deleted successfully");
        return ResponseEntity.ok(response);
    }

    // GET by blockFloor
    @GetMapping("/floor/{blockFloor}")
    public List<Block> getByFloor(@PathVariable int blockFloor) {
        return blockService.getBlocksByFloor(blockFloor);
    }

    // GET by blockCode
    @GetMapping("/code/{blockCode}")
    public List<Block> getByCode(@PathVariable int blockCode) {
        return blockService.getBlocksByCode(blockCode);
    }
}