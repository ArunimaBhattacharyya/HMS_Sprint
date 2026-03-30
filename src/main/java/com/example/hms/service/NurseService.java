package com.example.hms.service;

import com.example.hms.entity.Nurse;
import com.example.hms.repository.NurseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class NurseService {

    @Autowired
    private NurseRepository nurseRepository;

    //get all nurse
    public List<Nurse> getAllNurses(){
        return nurseRepository.findAll();
    }

    //get nurse by Id
    public Optional<Nurse> getNurseById(int id) {
        return nurseRepository.findById(id);
    }

    //get nurse by name
    public List<Nurse> getNurseByName(String name){
        return nurseRepository.findByName(name);
    }

    //get nurse by position
    public List<Nurse> getNurseByPosition(String position){
        return nurseRepository.findByPosition(position);
    }

    //add new nurse
    public Nurse createNurse(Nurse nurse){
        return nurseRepository.save(nurse);
    }

    //update existing nurse
    public Nurse updateNurse(int id, Nurse updatedNurse){
        Nurse existingNurse = nurseRepository.findById(id).orElseThrow(() -> new RuntimeException("Nurse not found"));

        existingNurse.setName(updatedNurse.getName());
        existingNurse.setPosition(updatedNurse.getPosition());
        existingNurse.setRegistered(updatedNurse.getRegistered());
        existingNurse.setSsn(updatedNurse.getSsn());

        return nurseRepository.save(existingNurse);
    }

    //delete nurse
//    public void deleteNurse(int id){
//        if(!nurseRepository.existsById(id)){
//            throw new RuntimeException("Nurse not found."+id);
//        }
//
//        nurseRepository.deleteById(id);
//    }

}
