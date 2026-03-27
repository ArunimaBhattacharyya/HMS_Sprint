package com.example.hms.controller;

import com.example.hms.dto.TrainedInDTO;
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
    public List<TrainedInDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{physician}/{treatment}")
    public TrainedInDTO getById(@PathVariable int physician,
                                @PathVariable int treatment) {
        return service.getById(physician, treatment);
    }

    @GetMapping("/physician/{id}")
    public List<TrainedInDTO> getByPhysician(@PathVariable int id) {
        return service.getByPhysician(id);
    }

    @GetMapping("/procedure/{code}")
    public List<TrainedInDTO> getByProcedure(@PathVariable int code) {
        return service.getByProcedure(code);
    }

    @PostMapping
    public TrainedInDTO create(@RequestBody TrainedInDTO dto) {
        return service.save(dto);
    }

    @PutMapping("/{physician}/{treatment}")
    public TrainedInDTO update(@PathVariable int physician,
                               @PathVariable int treatment,
                               @RequestBody TrainedInDTO dto) {
        return service.update(physician, treatment, dto);
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