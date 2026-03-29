package com.example.hms.service;

import com.example.hms.dto.StayRequest;
import com.example.hms.dto.StayResponse;
import com.example.hms.entity.Patient;
import com.example.hms.entity.Room;
import com.example.hms.entity.Stay;
import com.example.hms.repository.PatientRepository;
import com.example.hms.repository.RoomRepository;
import com.example.hms.repository.StayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StayService {

    @Autowired
    private StayRepository stayRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private RoomRepository roomRepository;

   //create
    public StayResponse createStay(StayRequest request) {

        Stay stay = new Stay();

        stay.setStayId(request.getStayId());

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        stay.setPatient(patient);
        stay.setRoom(room);
        stay.setStayStart(request.getStayStart());
        stay.setStayEnd(request.getStayEnd());

        Stay saved = stayRepository.save(stay);

        return convertToResponse(saved);
    }

    //read
    public List<StayResponse> getAllStay() {
        return stayRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // read by id
    public StayResponse getStayById(int id) {
        Stay stay = stayRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stay not found"));

        return convertToResponse(stay);
    }

    //get stay by patient
    public List<StayResponse> getStayByPatient(int patientId) {

        List<Stay> stays = stayRepository.findByPatient_Ssn(patientId);

        if (stays.isEmpty()) {
            throw new RuntimeException("No stay found for patient: " + patientId);
        }

        List<StayResponse> responseList = new java.util.ArrayList<>();

        for (Stay stay : stays) {
            responseList.add(convertToResponse(stay));
        }

        return responseList;
    }

    //get stay by room
    public List<StayResponse> getStayByRoom(int roomId) {

        List<Stay> stays = stayRepository.findByRoom_RoomNumber(roomId);

        if (stays.isEmpty()) {
            throw new RuntimeException("No stay found for room: " + roomId);
        }

        List<StayResponse> responseList = new java.util.ArrayList<>();

        for(Stay stay : stays){
            responseList.add(convertToResponse(stay));
        }

        return responseList;
    }

    // update
    public StayResponse updateStay(int id, StayRequest request) {

        Stay existing = stayRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stay not found"));

        if (request.getPatientId() != 0) {
            Patient patient = patientRepository.findById(request.getPatientId()).get();
            existing.setPatient(patient);
        }

        if (request.getRoomId() != 0) {
            Room room = roomRepository.findById(request.getRoomId()).get();
            existing.setRoom(room);
        }

        if (request.getStayStart() != null) {
            existing.setStayStart(request.getStayStart());
        }

        if (request.getStayEnd() != null) {
            existing.setStayEnd(request.getStayEnd());
        }

        Stay updated = stayRepository.save(existing);

        return convertToResponse(updated);
    }

    // delete
    public void deleteStay(int id) {

        stayRepository.deleteById(id);
    }


    // converter
    private StayResponse convertToResponse(Stay stay) {

        StayResponse response = new StayResponse();

        response.setStayId(stay.getStayId());

        response.setPatientId(stay.getPatient().getSsn());
        response.setRoomId(stay.getRoom().getRoomNumber());

        response.setStayStart(stay.getStayStart());
        response.setStayEnd(stay.getStayEnd());

        return response;
    }


}