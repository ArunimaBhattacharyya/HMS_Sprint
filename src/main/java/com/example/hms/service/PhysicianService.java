package com.example.hms.service;

import com.example.hms.entity.Physician;
import com.example.hms.repository.PhysicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class PhysicianService {

    @Autowired
    private PhysicianRepository physicianRepository;

    public List<Physician> getAllPhysicians() {

        return physicianRepository.findAll();
    }

    public Physician getPhysicianById(int id) {

        return physicianRepository.findById(id).orElse(null);
    }

    //Get physician by name
    public List<Physician> getPhysicianByName(String name){

        return physicianRepository.findByName(name);
    }

    //Get physician by position
    public List<Physician> getPhysicianByPosition(String position){

        return physicianRepository.findByPosition(position);
    }

    public Physician addPhysician(Physician physician) {

        return physicianRepository.save(physician);
    }

    //Update Existing Physician
    public Physician updatePhysician(int id, Physician updatedPhysician) {

        Physician existingPhysician = physicianRepository.findById(id).orElseThrow(() -> new RuntimeException("Physician not found"));

        existingPhysician.setName(updatedPhysician.getName());
        existingPhysician.setPosition(updatedPhysician.getPosition());
        existingPhysician.setSsn(updatedPhysician.getSsn());

        return physicianRepository.save(existingPhysician);
    }

    public void deletePhysician(int id) {

        if(!physicianRepository.existsById(id)){
            throw new RuntimeException("Physician not found."+id);
        }
        physicianRepository.deleteById(id);
    }
}
