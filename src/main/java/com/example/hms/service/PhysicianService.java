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

    public Physician addPhysician(Physician physician) {

        return physicianRepository.save(physician);
    }

    public Physician updatePhysician(int id, Physician physician) {
        physician.setEmployeeId(id);
        return physicianRepository.save(physician);
    }

    public void deletePhysician(int id) {

        physicianRepository.deleteById(id);
    }
}
