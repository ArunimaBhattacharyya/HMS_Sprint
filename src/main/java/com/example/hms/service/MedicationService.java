package com.example.hms.service;

import com.example.hms.entity.Medication;
import com.example.hms.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicationService {

    @Autowired
    private MedicationRepository medicationRepository;

    //get all medication
    public List<Medication> getAllMedication (){
        return medicationRepository.findAll();
    }

    //get medication by id
    public Optional<Medication> getMedicaltionByCode(int code) {
        return medicationRepository.findById(code);
    }

    //get medication by name
    public List<Medication> getMedicationByName(String name) {
        return medicationRepository.findByName(name);
    }

    //get medication by brand
    public List<Medication> getMedicationByBrand(String brand){
        return medicationRepository.findByBrand(brand);
    }

    //add new medication
    public Medication createMedication(Medication medication){
        return medicationRepository.save(medication);
    }

    //update existing medication
    public Medication updateMedication(int code, Medication updatedMedication){
        Medication existingMedication = medicationRepository.findById(code).orElseThrow(() -> new RuntimeException("Medication not found"));

        existingMedication.setName(updatedMedication.getName());
        existingMedication.setBrand(updatedMedication.getBrand());
        existingMedication.setDescription(updatedMedication.getDescription());
        return medicationRepository.save(existingMedication);
    }



}
