package com.example.hms.service;

import com.example.hms.dto.AffiliatedWithRequest;
import com.example.hms.dto.AffiliatedWithResponse;
import com.example.hms.entity.AffiliatedWith;
import com.example.hms.entity.AffiliatedWithId;
import com.example.hms.repository.AffiliatedWithRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AffiliatedWithService {

    @Autowired
    private AffiliatedWithRepository affiliatedWithRepository;

    // Helper method to map Entity to DTO
    private AffiliatedWithResponse mapToResponse(AffiliatedWith affiliation) {
        if (affiliation == null) return null;

        AffiliatedWithResponse response = new AffiliatedWithResponse();
        response.setPhysician(affiliation.getPhysician());
        response.setDepartment(affiliation.getDepartment());
        response.setPrimaryAffiliation(affiliation.getPrimaryAffiliation());

        // Extract related names if the linked entities exist
        if (affiliation.getPhysicianEntity() != null) {
            response.setPhysicianName(affiliation.getPhysicianEntity().getName());
        }
        if (affiliation.getDepartmentEntity() != null) {
            response.setDepartmentName(affiliation.getDepartmentEntity().getName());
        }

        return response;
    }

    public List<AffiliatedWithResponse> getAllAffiliations() {
        return affiliatedWithRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public AffiliatedWithResponse getAffiliationById(int physicianId, int departmentId) {
        AffiliatedWithId id = new AffiliatedWithId(physicianId, departmentId);
        AffiliatedWith affiliation = affiliatedWithRepository.findById(id).orElse(null);
        return mapToResponse(affiliation);
    }

    public List<AffiliatedWithResponse> getAffiliationsByPhysician(int physicianId) {
        return affiliatedWithRepository.findByPhysician(physicianId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<AffiliatedWithResponse> getAffiliationsByDepartment(int departmentId) {
        return affiliatedWithRepository.findByDepartment(departmentId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public AffiliatedWithResponse addAffiliation(AffiliatedWithRequest request) {
        AffiliatedWith affiliation = new AffiliatedWith();
        affiliation.setPhysician(request.getPhysician());
        affiliation.setDepartment(request.getDepartment());
        affiliation.setPrimaryAffiliation(request.getPrimaryAffiliation());

        AffiliatedWith savedAffiliation = affiliatedWithRepository.save(affiliation);
        return mapToResponse(savedAffiliation);
    }

    // Update Existing Affiliation (Updating primaryAffiliation flag)
    public AffiliatedWithResponse updateAffiliation(int physicianId, int departmentId, AffiliatedWithRequest request) {
        AffiliatedWithId id = new AffiliatedWithId(physicianId, departmentId);
        AffiliatedWith existingAffiliation = affiliatedWithRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Affiliation not found"));

        existingAffiliation.setPrimaryAffiliation(request.getPrimaryAffiliation());

        AffiliatedWith updatedAffiliation = affiliatedWithRepository.save(existingAffiliation);
        return mapToResponse(updatedAffiliation);
    }

    public void deleteAffiliation(int physicianId, int departmentId) {
        AffiliatedWithId id = new AffiliatedWithId(physicianId, departmentId);
        if(!affiliatedWithRepository.existsById(id)){
            throw new RuntimeException("Affiliation not found for Physician: " + physicianId + " and Department: " + departmentId);
        }
        affiliatedWithRepository.deleteById(id);
    }
}