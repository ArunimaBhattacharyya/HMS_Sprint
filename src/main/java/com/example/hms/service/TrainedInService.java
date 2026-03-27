package com.example.hms.service;

import com.example.hms.entity.TrainedIn;
import com.example.hms.entity.TrainedInId;
import com.example.hms.repository.TrainedInRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainedInService {

    @Autowired
    private TrainedInRepository repo;

    public List<TrainedIn> getAll() {
        return repo.findAll();
    }

    public TrainedIn save(TrainedIn trainedIn) {
        TrainedInId id = trainedIn.getId();

        if (repo.existsById(id)) {
            throw new RuntimeException("Already exists");
        }

        return repo.save(trainedIn);
    }

    public TrainedIn getById(int physician, int treatment) {
        TrainedInId id = new TrainedInId(physician, treatment);
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }

    public List<TrainedIn> getByPhysician(int id) {
        return repo.findByPhysician_EmployeeId(id);
    }

    public List<TrainedIn> getByProcedure(int code) {
        return repo.findByProcedure_Code(code);
    }

    public TrainedIn update(int physician, int treatment, TrainedIn updated) {
        TrainedInId id = new TrainedInId(physician, treatment);

        TrainedIn existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        existing.setCertificationDate(updated.getCertificationDate());
        existing.setCertificationExpires(updated.getCertificationExpires());

        return repo.save(existing);
    }

    public void delete(int physician, int treatment) {
        TrainedInId id = new TrainedInId(physician, treatment);

        if (!repo.existsById(id)) {
            throw new RuntimeException("Not found");
        }

        repo.deleteById(id);
    }

    public boolean exists(int physician, int treatment) {
        TrainedInId id = new TrainedInId(physician, treatment);
        return repo.existsById(id);
    }
}