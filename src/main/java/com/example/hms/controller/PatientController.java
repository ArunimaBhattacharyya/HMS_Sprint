package com.example.hms.controller;

import com.example.hms.entity.Patient;
import com.example.hms.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientServices;

    @GetMapping
    public List<Patient> getAllPatients() {

        return patientServices.getAllPatients();
    }

    @GetMapping("/{ssn}")
    public Patient getPatientById(@PathVariable int ssn) {
        Optional<Patient> patient = patientServices.getPatientById(ssn);
        return patient.orElse(null);
    }

    @GetMapping("/name/{name}")
    public List<Patient> getPatientByName(@PathVariable String name) {

        return patientServices.getPatientByName(name);
    }

    @GetMapping("/pcp/{pcp}")
    public List<Patient> getPatientByPcp(@PathVariable Integer pcp) {

        return patientServices.getPatientByPcp(pcp);
    }

    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {

        return patientServices.createPatient(patient);
    }

    @PutMapping("/{ssn}")
    public Patient updatePatient(@PathVariable int ssn, @RequestBody Patient patient) {
        return patientServices.updatePatient(ssn, patient);
    }

    @DeleteMapping("/{ssn}")
    public String deletePatient(@PathVariable int ssn) {
        patientServices.deletePatient(ssn);
        return "Patient deleted successfully";
    }
}
