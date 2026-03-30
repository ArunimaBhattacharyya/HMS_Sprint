package com.example.hms.controller;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.hms.dto.DepartmentRequest;
import com.example.hms.dto.DepartmentResponse;
import com.example.hms.service.DepartmentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

public class DepartmentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController controller;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetAllDepartments() throws Exception {
        when(departmentService.getAllDepartments()).thenReturn(List.of());
        mockMvc.perform(get("/departments"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetDepartmentById() throws Exception {
        DepartmentResponse response = new DepartmentResponse();
        response.setDepartmentId(1);
        response.setName("General Medicine");
        response.setHead(4);

        when(departmentService.getDepartmentById(1)).thenReturn(response);
        mockMvc.perform(get("/departments/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetDepartmentByName() throws Exception {
        DepartmentResponse response = new DepartmentResponse();
        response.setDepartmentId(1);
        response.setName("General Medicine");
        response.setHead(4);

        when(departmentService.getDepartmentByName("General Medicine")).thenReturn(List.of(response));
        mockMvc.perform(get("/departments/name/General Medicine"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetDepartmentByHead() throws Exception {
        DepartmentResponse response = new DepartmentResponse();
        response.setDepartmentId(1);
        response.setName("General Medicine");
        response.setHead(4);

        when(departmentService.getDepartmentByHead(4)).thenReturn(List.of(response));
        mockMvc.perform(get("/departments/head/4"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddDepartment() throws Exception {
        DepartmentResponse response = new DepartmentResponse();
        response.setDepartmentId(1);
        response.setName("General Medicine");
        response.setHead(4);

        when(departmentService.addDepartment(any(DepartmentRequest.class))).thenReturn(response);
        mockMvc.perform(post("/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"departmentId\":1,\"name\":\"General Medicine\",\"head\":4}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateDepartment() throws Exception {
        DepartmentResponse updatedResponse = new DepartmentResponse();
        updatedResponse.setDepartmentId(1);
        updatedResponse.setName("Advanced Medicine");
        updatedResponse.setHead(4);

        when(departmentService.updateDepartment(eq(1), any(DepartmentRequest.class))).thenReturn(updatedResponse);
        mockMvc.perform(put("/departments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"departmentId\":1,\"name\":\"Advanced Medicine\",\"head\":4}"))
                .andExpect(status().isOk());
    }
}
