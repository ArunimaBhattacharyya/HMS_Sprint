package com.example.hms.controller;

import com.example.hms.dto.StayRequest;
import com.example.hms.dto.StayResponse;
import com.example.hms.service.StayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stays")
public class StayController {

    @Autowired
    private StayService stayService;

    // CREATE
    @PostMapping("/create")
    public StayResponse create(@RequestBody StayRequest request) {
        return stayService.createStay(request);
    }

    // READ ALL
    @GetMapping("/all")
    public List<StayResponse> readAll() {
        return stayService.getAllStay();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public StayResponse readById(@PathVariable int id) {
        return stayService.getStayById(id);
    }

    @GetMapping("/patient/{patientId}")
    public List<StayResponse> getByPatient(@PathVariable int patientId) {
        return stayService.getStayByPatient(patientId);
    }

    @GetMapping("/room/{roomId}")
    public List<StayResponse> getByRoom(@PathVariable int roomId) {
        return stayService.getStayByRoom(roomId);
    }

    // UPDATE
    @PutMapping("/{id}")
    public StayResponse update(@PathVariable int id, @RequestBody StayRequest request) {
        return stayService.updateStay(id, request);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        stayService.deleteStay(id);
        return "Deleted Successfully";
    }
}