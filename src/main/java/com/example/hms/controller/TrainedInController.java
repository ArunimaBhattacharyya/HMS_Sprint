package com.example.hms.controller;

import com.example.hms.entity.TrainedIn;
import com.example.hms.service.TrainedInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainedin")
public class TrainedInController {

    @Autowired
    private TrainedInService service;

    @GetMapping
    public List<TrainedIn> getAll() {
        return service.getAll();
    }

    @GetMapping("/{physician}/{treatment}")
    public TrainedIn getById(@PathVariable int physician,
                             @PathVariable int treatment) {
        return service.getById(physician, treatment);
    }

    @GetMapping("/physician/{id}")
    public List<TrainedIn> getByPhysician(@PathVariable int id) {
        return service.getByPhysician(id);
    }

    @GetMapping("/procedure/{code}")
    public List<TrainedIn> getByProcedure(@PathVariable int code) {
        return service.getByProcedure(code);
    }

    @PostMapping
    public TrainedIn create(@RequestBody TrainedIn trainedIn) {
        return service.save(trainedIn);
    }

    @PutMapping("/{physician}/{treatment}")
    public TrainedIn update(@PathVariable int physician,
                            @PathVariable int treatment,
                            @RequestBody TrainedIn trainedIn) {
        return service.update(physician, treatment, trainedIn);
    }

    @DeleteMapping("/{physician}/{treatment}")
    public String delete(@PathVariable int physician,
                         @PathVariable int treatment) {
        service.delete(physician, treatment);
        return "Deleted successfully";
    }

    @GetMapping("/check/{physician}/{treatment}")
    public boolean isTrained(@PathVariable int physician,
                             @PathVariable int treatment) {
        return service.exists(physician, treatment);
    }
}