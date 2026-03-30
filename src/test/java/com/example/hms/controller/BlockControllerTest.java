package com.example.hms.controller;

import com.example.hms.entity.Block;
import com.example.hms.service.BlockService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BlockControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BlockService blockService;

    @InjectMocks
    private BlockController controller;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetAllBlocks() throws Exception {
        when(blockService.getAllBlock()).thenReturn(List.of());

        mockMvc.perform(get("/blocks/allblock"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateBlock() throws Exception {
        Block block = new Block();
        block.setBlockFloor(1);
        block.setBlockCode(101);

        when(blockService.saveBlock(any(Block.class))).thenReturn(block);

        mockMvc.perform(post("/blocks/createblock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"blockFloor\":1,\"blockCode\":101}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateBlock() throws Exception {
        Block updated = new Block();
        updated.setBlockFloor(2);
        updated.setBlockCode(202);

        when(blockService.updateBlock(eq(1), eq(101), any(Block.class)))
                .thenReturn(updated);

        mockMvc.perform(put("/blocks/updateblock/1/101")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"blockFloor\":2,\"blockCode\":202}"))
                .andExpect(status().isOk());
    }

//    @Test
//    public void testDeleteBlock() throws Exception {
//        mockMvc.perform(delete("/blocks/deleteblock/1/101"))
//                .andExpect(status().isOk());
//    }

    @Test
    public void testGetByFloor() throws Exception {
        Block b = new Block();
        b.setBlockFloor(1);
        b.setBlockCode(101);

        when(blockService.getBlocksByFloor(1)).thenReturn(List.of(b));

        mockMvc.perform(get("/blocks/floor/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetByCode() throws Exception {
        Block b = new Block();
        b.setBlockFloor(1);
        b.setBlockCode(101);

        when(blockService.getBlocksByCode(101)).thenReturn(List.of(b));

        mockMvc.perform(get("/blocks/code/101"))
                .andExpect(status().isOk());
    }
}