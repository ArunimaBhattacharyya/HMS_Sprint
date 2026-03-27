package com.example.hms.controller;

import com.example.hms.entity.AffiliatedWith;
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
    public List<AffiliatedWith> getAllAffiliations() throws Exception {
        return affiliatedWithService.getAllAffiliations();
    }

    //GET by Composite ID
    @GetMapping("/{physicianId}/{departmentId}")
    public AffiliatedWith getAffiliationById(@PathVariable int physicianId, @PathVariable int departmentId) throws Exception {
        return affiliatedWithService.getAffiliationById(physicianId, departmentId);
    }

    //GET /affiliations/physician/{physicianId}
    @GetMapping("/physician/{physicianId}")
    public List<AffiliatedWith> getAffiliationsByPhysician(@PathVariable int physicianId) {
        return affiliatedWithService.getAffiliationsByPhysician(physicianId);
    }

    //GET /affiliations/department/{departmentId}
    @GetMapping("/department/{departmentId}")
    public List<AffiliatedWith> getAffiliationsByDepartment(@PathVariable int departmentId) {
        return affiliatedWithService.getAffiliationsByDepartment(departmentId);
    }

    //POST (create)
    @PostMapping
    public AffiliatedWith addAffiliation(@RequestBody AffiliatedWith affiliatedWith) throws Exception {
        return affiliatedWithService.addAffiliation(affiliatedWith);
    }

    //PUT (update) - Requires both IDs in path
    @PutMapping("/{physicianId}/{departmentId}")
    public AffiliatedWith updateAffiliation(@PathVariable int physicianId,
                                            @PathVariable int departmentId,
                                            @RequestBody AffiliatedWith affiliatedWith) throws Exception {
        return affiliatedWithService.updateAffiliation(physicianId, departmentId, affiliatedWith);
    }

    //DELETE - Requires both IDs in path
    @DeleteMapping("/{physicianId}/{departmentId}")
    public String deleteAffiliation(@PathVariable int physicianId, @PathVariable int departmentId) throws Exception {
        affiliatedWithService.deleteAffiliation(physicianId, departmentId);
        return "Affiliation deleted successfully";
    }

}
