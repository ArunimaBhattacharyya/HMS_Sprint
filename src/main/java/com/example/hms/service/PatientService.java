package com.example.hms.service;

import com.example.hms.entity.Patient;
import com.example.hms.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(int ssn) {
        return patientRepository.findById(ssn);
    }

    public List<Patient> getPatientByName(String name) {
        return patientRepository.findByName(name);
    }

    public List<Patient> getPatientByPcp(Integer pcp) {
        return patientRepository.findByPcp(pcp);
    }

    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient updatePatient(int ssn, Patient updatedPatient) {
        Patient existingPatient = patientRepository.findById(ssn)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        existingPatient.setName(updatedPatient.getName());
        existingPatient.setAddress(updatedPatient.getAddress());
        existingPatient.setPhone(updatedPatient.getPhone());
        existingPatient.setInsuranceId(updatedPatient.getInsuranceId());
        existingPatient.setPcp(updatedPatient.getPcp());

        return patientRepository.save(existingPatient);
    }

    public void deletePatient(int ssn) {
        if (!patientRepository.existsById(ssn)) {
            throw new RuntimeException("Patient not found: " + ssn);
        }
        patientRepository.deleteById(ssn);
    }
}
