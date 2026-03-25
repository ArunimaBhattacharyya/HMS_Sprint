package com.example.hms.service;

import com.example.hms.dto.OnCallRequest;
import com.example.hms.dto.OnCallResponse;
import com.example.hms.entity.*;
import com.example.hms.repository.BlockRepository;
import com.example.hms.repository.NurseRepository;
import com.example.hms.repository.OnCallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OnCallService {

    @Autowired
    private OnCallRepository onCallRepository;

    @Autowired
    private NurseRepository nurseRepository;

    @Autowired
    private BlockRepository blockRepository;

    // create
    public OnCallResponse create(OnCallRequest request){

        Nurse nurse = nurseRepository.findById(request.getNurseId())
                .orElseThrow(() -> new RuntimeException("Nurse not found"));

        BlockId blockId = new BlockId(request.getBlockFloor(), request.getBlockCode());

        Block block = blockRepository.findById(blockId)
                .orElseThrow(() -> new RuntimeException("Block not found"));

        OnCallId id = new OnCallId(nurse, block, request.getOnCallStart());

        OnCall onCall = new OnCall();
        onCall.setId(id);
        onCall.setOnCallEnd(request.getOnCallEnd());

        OnCall saved = onCallRepository.save(onCall);

        return mapToResponse(saved);
    }

    // get all
    public List<OnCallResponse> getAll() {
        return onCallRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // get by nurse
    public List<OnCallResponse> getByNurse(int nurseId){
        return onCallRepository.findById_Nurse_EmployeeId(nurseId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    //mapping
    private OnCallResponse mapToResponse(OnCall onCall) {

        OnCallResponse res = new OnCallResponse();

        res.setNurseId(onCall.getId().getNurse().getEmployeeId());
        res.setNurseName(onCall.getId().getNurse().getName());

        res.setBlockFloor(onCall.getId().getBlock().getBlockFloor());
        res.setBlockCode(onCall.getId().getBlock().getBlockCode());

        res.setOnCallStart(onCall.getId().getOnCallStart());
        res.setOnCallEnd(onCall.getOnCallEnd());

        return res;
    }
}