package com.example.hms.service;

import com.example.hms.entity.Block;
import com.example.hms.entity.BlockId;
import com.example.hms.repository.BlockRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

public class BlockServiceTest {

    @Mock
    private BlockRepository blockRepository;

    @InjectMocks
    private BlockService blockService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllBlocks() {
        Block b1 = new Block();
        b1.setBlockFloor(1);
        b1.setBlockCode(101);

        Block b2 = new Block();
        b2.setBlockFloor(2);
        b2.setBlockCode(102);

        when(blockRepository.findAll()).thenReturn(List.of(b1, b2));

        List<Block> result = blockService.getAllBlock();
        assertEquals(2, result.size());
    }

    @Test
    public void testCreateBlock() {
        Block block = new Block();
        block.setBlockFloor(1);
        block.setBlockCode(101);

        when(blockRepository.save(block)).thenReturn(block);

        Block result = blockService.saveBlock(block);
        assertEquals(101, result.getBlockCode());
    }

    @Test
    public void testUpdateBlock() {
        Block existing = new Block();
        existing.setBlockFloor(1);
        existing.setBlockCode(101);

        Block updated = new Block();
        updated.setBlockFloor(2);
        updated.setBlockCode(202);

        BlockId oldId = new BlockId(1, 101);
        BlockId newId = new BlockId(2, 202);

        when(blockRepository.findById(oldId)).thenReturn(Optional.of(existing));
        when(blockRepository.existsById(newId)).thenReturn(false);
        when(blockRepository.save(any(Block.class))).thenReturn(updated);

        Block result = blockService.updateBlock(1, 101, updated);

        assertEquals(2, result.getBlockFloor());
        assertEquals(202, result.getBlockCode());
    }

    @Test
    public void testUpdateBlock_NotFound() {
        Block updated = new Block();
        updated.setBlockFloor(2);
        updated.setBlockCode(202);

        when(blockRepository.findById(any(BlockId.class))).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> blockService.updateBlock(1, 101, updated));

        assertTrue(ex.getMessage().contains("Block not found"));
    }

//    @Test
//    public void testDeleteBlock() {
//        Block block = new Block();
//        block.setBlockFloor(1);
//        block.setBlockCode(101);
//
//        BlockId id = new BlockId(1, 101);
//
//        when(blockRepository.findById(id)).thenReturn(Optional.of(block));
//
//        blockService.deleteBlock(1, 101);
//
//        verify(blockRepository).delete(block);
//    }

    @Test
    public void testGetBlocksByFloor() {
        Block b = new Block();
        b.setBlockFloor(1);
        b.setBlockCode(101);

        when(blockRepository.findByBlockFloor(1)).thenReturn(List.of(b));

        List<Block> result = blockService.getBlocksByFloor(1);

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getBlockFloor());
    }

    @Test
    public void testGetBlocksByCode() {
        Block b = new Block();
        b.setBlockFloor(1);
        b.setBlockCode(101);

        when(blockRepository.findByBlockCode(101)).thenReturn(List.of(b));

        List<Block> result = blockService.getBlocksByCode(101);

        assertEquals(1, result.size());
        assertEquals(101, result.get(0).getBlockCode());
    }
}