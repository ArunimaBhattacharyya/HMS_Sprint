package com.example.hms.service;

import com.example.hms.entity.AffiliatedWith;
import com.example.hms.entity.AffiliatedWithId;
import com.example.hms.repository.AffiliatedWithRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AffiliatedWithService {

    @Autowired
    private AffiliatedWithRepository affiliatedWithRepository;

    public List<AffiliatedWith> getAllAffiliations() {
        return affiliatedWithRepository.findAll();
    }

    public AffiliatedWith getAffiliationById(int physicianId, int departmentId) {
        AffiliatedWithId id = new AffiliatedWithId(physicianId, departmentId);
        return affiliatedWithRepository.findById(id).orElse(null);
    }

    public List<AffiliatedWith> getAffiliationsByPhysician(int physicianId) {
        return affiliatedWithRepository.findByPhysician(physicianId);
    }

    public List<AffiliatedWith> getAffiliationsByDepartment(int departmentId) {
        return affiliatedWithRepository.findByDepartment(departmentId);
    }

    public AffiliatedWith addAffiliation(AffiliatedWith affiliatedWith) {
        return affiliatedWithRepository.save(affiliatedWith);
    }

    //Update Existing Affiliation (Updating primaryAffiliation flag)
    public AffiliatedWith updateAffiliation(int physicianId, int departmentId, AffiliatedWith updatedAffiliation) {
        AffiliatedWithId id = new AffiliatedWithId(physicianId, departmentId);
        AffiliatedWith existingAffiliation = affiliatedWithRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Affiliation not found"));

        existingAffiliation.setPrimaryAffiliation(updatedAffiliation.getPrimaryAffiliation());

        return affiliatedWithRepository.save(existingAffiliation);
    }

    public void deleteAffiliation(int physicianId, int departmentId) {
        AffiliatedWithId id = new AffiliatedWithId(physicianId, departmentId);
        if(!affiliatedWithRepository.existsById(id)){
            throw new RuntimeException("Affiliation not found for Physician: " + physicianId + " and Department: " + departmentId);
        }
        affiliatedWithRepository.deleteById(id);
    }
}
