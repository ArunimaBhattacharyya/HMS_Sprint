package com.example.hms.service;

import com.example.hms.dto.OnCallRequest;
import com.example.hms.dto.OnCallResponse;
import com.example.hms.entity.*;
import com.example.hms.repository.BlockRepository;
import com.example.hms.repository.NurseRepository;
import com.example.hms.repository.OnCallRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OnCallServiceTest {

    @Mock
    private OnCallRepository onCallRepository;

    @Mock
    private NurseRepository nurseRepository;

    @Mock
    private BlockRepository blockRepository;

    @InjectMocks
    private OnCallService onCallService;

    private Nurse nurse;
    private Block block;
    private OnCall onCall;
    private LocalDateTime start;
    private LocalDateTime end;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        start = LocalDateTime.of(2024, 1, 1, 8, 0);
        end   = LocalDateTime.of(2024, 1, 1, 16, 0);

        nurse = new Nurse(1, "Arunima", "Head Nurse", 1, 100000001);

        block = new Block();
        block.setBlockFloor(1);
        block.setBlockCode(101);

        OnCallId onCallId = new OnCallId(nurse, block, start);
        onCall = new OnCall();
        onCall.setId(onCallId);
        onCall.setOnCallEnd(end);
    }

    @Test
    public void testCreate() {
        OnCallRequest request = new OnCallRequest();
        request.setNurseId(1);
        request.setBlockFloor(1);
        request.setBlockCode(101);
        request.setOnCallStart(start);
        request.setOnCallEnd(end);

        when(nurseRepository.findById(1)).thenReturn(Optional.of(nurse));
        when(blockRepository.findById(new BlockId(1, 101))).thenReturn(Optional.of(block));
        when(onCallRepository.save(any(OnCall.class))).thenReturn(onCall);

        OnCallResponse result = onCallService.create(request);

        assertEquals(1, result.getNurseId());
        assertEquals("Arunima", result.getNurseName());
        assertEquals(1, result.getBlockFloor());
        assertEquals(101, result.getBlockCode());
        assertEquals(start, result.getOnCallStart());
        assertEquals(end, result.getOnCallEnd());
    }

    @Test
    public void testCreate_NurseNotFound() {
        OnCallRequest request = new OnCallRequest();
        request.setNurseId(99);
        request.setBlockFloor(1);
        request.setBlockCode(101);
        request.setOnCallStart(start);
        request.setOnCallEnd(end);

        when(nurseRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> onCallService.create(request));

        assertEquals("Nurse not found", ex.getMessage());
    }

    @Test
    public void testCreate_BlockNotFound() {
        OnCallRequest request = new OnCallRequest();
        request.setNurseId(1);
        request.setBlockFloor(99);
        request.setBlockCode(999);
        request.setOnCallStart(start);
        request.setOnCallEnd(end);

        when(nurseRepository.findById(1)).thenReturn(Optional.of(nurse));
        when(blockRepository.findById(new BlockId(99, 999))).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> onCallService.create(request));

        assertEquals("Block not found", ex.getMessage());
        verify(onCallRepository, never()).save(any());
    }

    @Test
    public void testGetAll() {
        when(onCallRepository.findAll()).thenReturn(List.of(onCall));

        List<OnCallResponse> result = onCallService.getAll();

        assertEquals(1, result.size());
        assertEquals("Arunima", result.get(0).getNurseName());
        verify(onCallRepository).findAll();
    }

    @Test
    public void testGetByNurse() {
        when(onCallRepository.findById_Nurse_EmployeeId(1)).thenReturn(List.of(onCall));

        List<OnCallResponse> result = onCallService.getByNurse(1);

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getNurseId());
        verify(onCallRepository).findById_Nurse_EmployeeId(1);
    }

    @Test
    public void testGetByFloor() {
        when(onCallRepository.findById_Block_BlockFloor(1)).thenReturn(List.of(onCall));

        List<OnCallResponse> result = onCallService.getByFloor(1);

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getBlockFloor());
        verify(onCallRepository).findById_Block_BlockFloor(1);
    }

    @Test
    public void testGetByCode() {
        when(onCallRepository.findById_Block_BlockCode(101)).thenReturn(List.of(onCall));

        List<OnCallResponse> result = onCallService.getByCode(101);

        assertEquals(1, result.size());
        assertEquals(101, result.get(0).getBlockCode());
        verify(onCallRepository).findById_Block_BlockCode(101);
    }
}
