package com.example.hms.controller;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.hms.dto.AffiliatedWithRequest;
import com.example.hms.dto.AffiliatedWithResponse;
import com.example.hms.service.AffiliatedWithService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

public class AffiliatedWithControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AffiliatedWithService affiliatedWithService;

    @InjectMocks
    private AffiliatedWithController controller;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetAllAffiliations() throws Exception {
        when(affiliatedWithService.getAllAffiliations()).thenReturn(List.of());
        mockMvc.perform(get("/affiliations"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAffiliationById() throws Exception {
        AffiliatedWithResponse response = new AffiliatedWithResponse();
        response.setPhysician(1);
        response.setDepartment(101);
        response.setPrimaryAffiliation(true);

        when(affiliatedWithService.getAffiliationById(1, 101)).thenReturn(response);
        mockMvc.perform(get("/affiliations/1/101"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAffiliationsByPhysician() throws Exception {
        AffiliatedWithResponse response = new AffiliatedWithResponse();
        response.setPhysician(1);
        response.setDepartment(101);
        response.setPrimaryAffiliation(true);

        when(affiliatedWithService.getAffiliationsByPhysician(1)).thenReturn(List.of(response));
        mockMvc.perform(get("/affiliations/physician/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAffiliationsByDepartment() throws Exception {
        AffiliatedWithResponse response = new AffiliatedWithResponse();
        response.setPhysician(1);
        response.setDepartment(101);
        response.setPrimaryAffiliation(true);

        when(affiliatedWithService.getAffiliationsByDepartment(101)).thenReturn(List.of(response));
        mockMvc.perform(get("/affiliations/department/101"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddAffiliation() throws Exception {
        AffiliatedWithResponse response = new AffiliatedWithResponse();
        response.setPhysician(1);
        response.setDepartment(101);
        response.setPrimaryAffiliation(true);

        when(affiliatedWithService.addAffiliation(any(AffiliatedWithRequest.class))).thenReturn(response);
        mockMvc.perform(post("/affiliations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"physician\":1,\"department\":101,\"primaryAffiliation\":true}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateAffiliation() throws Exception {
        AffiliatedWithResponse updatedResponse = new AffiliatedWithResponse();
        updatedResponse.setPhysician(1);
        updatedResponse.setDepartment(101);
        updatedResponse.setPrimaryAffiliation(false);

        when(affiliatedWithService.updateAffiliation(eq(1), eq(101), any(AffiliatedWithRequest.class))).thenReturn(updatedResponse);
        mockMvc.perform(put("/affiliations/1/101")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"physician\":1,\"department\":101,\"primaryAffiliation\":false}"))
                .andExpect(status().isOk());
    }
}
