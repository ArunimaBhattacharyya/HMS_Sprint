package com.example.hms.service;

import com.example.hms.dto.StayRequest;
import com.example.hms.dto.StayResponse;
import com.example.hms.entity.Patient;
import com.example.hms.entity.Room;
import com.example.hms.entity.Stay;
import com.example.hms.repository.PatientRepository;
import com.example.hms.repository.RoomRepository;
import com.example.hms.repository.StayRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class StayServiceTest {

    @Mock
    private StayRepository stayRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private StayService stayService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private StayRequest mockRequest() {
        StayRequest req = new StayRequest();
        req.setStayId(1);
        req.setPatientId(100);
        req.setRoomId(101);
        req.setStayStart(LocalDateTime.now());
        req.setStayEnd(LocalDateTime.now().plusDays(1));
        return req;
    }

    private Patient mockPatient(int id) {
        Patient p = new Patient();
        p.setSsn(id);
        return p;
    }

    private Room mockRoom(int id) {
        Room r = new Room();
        r.setRoomNumber(id);
        return r;
    }

    private Stay mockStay(Patient p, Room r) {
        Stay s = new Stay();
        s.setStayId(1);
        s.setPatient(p);
        s.setRoom(r);
        s.setStayStart(LocalDateTime.now());
        s.setStayEnd(LocalDateTime.now().plusDays(1));
        return s;
    }

    //  CREATE
    @Test
    public void testCreateStay() {
        StayRequest req = mockRequest();
        Patient patient = mockPatient(100);
        Room room = mockRoom(101);
        Stay stay = mockStay(patient, room);

        when(patientRepository.findById(100)).thenReturn(Optional.of(patient));
        when(roomRepository.findById(101)).thenReturn(Optional.of(room));
        when(stayRepository.save(any(Stay.class))).thenReturn(stay);

        StayResponse res = stayService.createStay(req);

        assertEquals(1, res.getStayId());
        assertEquals(100, res.getPatientId());
    }

    @Test
    public void testCreateStay_PatientNotFound() {
        StayRequest req = mockRequest();

        when(patientRepository.findById(anyInt())).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> stayService.createStay(req));

        assertEquals("Patient not found", ex.getMessage());
    }

    // GET ALL
    @Test
    public void testGetAllStay() {
        Patient p = mockPatient(100);
        Room r = mockRoom(101);
        Stay stay = mockStay(p, r);

        when(stayRepository.findAll()).thenReturn(List.of(stay));

        List<StayResponse> result = stayService.getAllStay();

        assertEquals(1, result.size());
    }

    //  GET BY ID
    @Test
    public void testGetStayById() {
        Patient p = mockPatient(100);
        Room r = mockRoom(101);
        Stay stay = mockStay(p, r);

        when(stayRepository.findById(1)).thenReturn(Optional.of(stay));

        StayResponse res = stayService.getStayById(1);

        assertEquals(1, res.getStayId());
    }

    @Test
    public void testGetStayById_NotFound() {
        when(stayRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> stayService.getStayById(1));

        assertEquals("Stay not found", ex.getMessage());
    }

    //  GET BY PATIENT
    @Test
    public void testGetStayByPatient() {
        Patient p = mockPatient(100);
        Room r = mockRoom(101);
        Stay stay = mockStay(p, r);

        when(stayRepository.findByPatient_Ssn(100)).thenReturn(List.of(stay));

        List<StayResponse> result = stayService.getStayByPatient(100);

        assertEquals(1, result.size());
    }

    @Test
    public void testGetStayByPatient_NotFound() {
        when(stayRepository.findByPatient_Ssn(100)).thenReturn(List.of());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> stayService.getStayByPatient(100));

        assertTrue(ex.getMessage().contains("No stay found"));
    }

    //  GET BY ROOM
    @Test
    public void testGetStayByRoom() {
        Patient p = mockPatient(100);
        Room r = mockRoom(101);
        Stay stay = mockStay(p, r);

        when(stayRepository.findByRoom_RoomNumber(101)).thenReturn(List.of(stay));

        List<StayResponse> result = stayService.getStayByRoom(101);

        assertEquals(1, result.size());
    }

    //  UPDATE
    @Test
    public void testUpdateStay() {
        StayRequest req = mockRequest();
        Patient p = mockPatient(100);
        Room r = mockRoom(101);
        Stay stay = mockStay(p, r);

        when(stayRepository.findById(1)).thenReturn(Optional.of(stay));
        when(patientRepository.findById(100)).thenReturn(Optional.of(p));
        when(roomRepository.findById(101)).thenReturn(Optional.of(r));
        when(stayRepository.save(any(Stay.class))).thenReturn(stay);

        StayResponse res = stayService.updateStay(1, req);

        assertEquals(1, res.getStayId());
    }

    @Test
    public void testUpdateStay_NotFound() {
        StayRequest req = mockRequest();

        when(stayRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> stayService.updateStay(1, req));

        assertEquals("Stay not found", ex.getMessage());
    }

//    //  DELETE
//    @Test
//    public void testDeleteStay() {
//        stayService.deleteStay(1);
//
//        verify(stayRepository).deleteById(1);
//    }
}