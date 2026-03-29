package com.example.hms.controller;

import com.example.hms.dto.AffiliatedWithRequest;
import com.example.hms.dto.AffiliatedWithResponse;
import com.example.hms.service.AffiliatedWithService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/affiliations")
public class AffiliatedWithController {

    @Autowired
    private AffiliatedWithService affiliatedWithService;

    //GET all
    @GetMapping
    public List<AffiliatedWithResponse> getAllAffiliations() throws Exception {
        return affiliatedWithService.getAllAffiliations();
    }

    //GET by Composite ID
    @GetMapping("/{physicianId}/{departmentId}")
    public AffiliatedWithResponse getAffiliationById(@PathVariable int physicianId, @PathVariable int departmentId) throws Exception {
        return affiliatedWithService.getAffiliationById(physicianId, departmentId);
    }

    //GET /affiliations/physician/{physicianId}
    @GetMapping("/physician/{physicianId}")
    public List<AffiliatedWithResponse> getAffiliationsByPhysician(@PathVariable int physicianId) {
        return affiliatedWithService.getAffiliationsByPhysician(physicianId);
    }

    //GET /affiliations/department/{departmentId}
    @GetMapping("/department/{departmentId}")
    public List<AffiliatedWithResponse> getAffiliationsByDepartment(@PathVariable int departmentId) {
        return affiliatedWithService.getAffiliationsByDepartment(departmentId);
    }

    //POST (create)
    @PostMapping
    public AffiliatedWithResponse addAffiliation(@RequestBody AffiliatedWithRequest request) throws Exception {
        return affiliatedWithService.addAffiliation(request);
    }

    //PUT (update) - Requires both IDs in path
    @PutMapping("/{physicianId}/{departmentId}")
    public AffiliatedWithResponse updateAffiliation(@PathVariable int physicianId,
                                                    @PathVariable int departmentId,
                                                    @RequestBody AffiliatedWithRequest request) throws Exception {
        return affiliatedWithService.updateAffiliation(physicianId, departmentId, request);
    }

    //DELETE - Requires both IDs in path
    @DeleteMapping("/{physicianId}/{departmentId}")
    public String deleteAffiliation(@PathVariable int physicianId, @PathVariable int departmentId) throws Exception {
        affiliatedWithService.deleteAffiliation(physicianId, departmentId);
        return "Affiliation deleted successfully";
    }
}