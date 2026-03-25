package com.example.hms.controller;

import com.example.hms.dto.OnCallRequest;
import com.example.hms.dto.OnCallResponse;
import com.example.hms.service.OnCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/oncall")
public class OnCallController {

    @Autowired
    private OnCallService onCallService;

    // Create
    @PostMapping
    public OnCallResponse create(@RequestBody OnCallRequest request) {
        return onCallService.create(request);
    }

    // get all
    @GetMapping
    public List<OnCallResponse> getAll() {
        return onCallService.getAll();
    }

    // get by nurse
    @GetMapping("/nurse/{id}")
    public List<OnCallResponse> getByNurse(@PathVariable int id) {
        return onCallService.getByNurse(id);
    }
}