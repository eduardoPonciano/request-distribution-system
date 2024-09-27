package br.com.eponciano.sac.relations.core.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.eponciano.sac.relations.core.controller.AttendantController;
import br.com.eponciano.sac.relations.core.model.Attendant;
import br.com.eponciano.sac.relations.core.model.SectorEnum;
import br.com.eponciano.sac.relations.core.service.AttendantService;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AttendantController.class)
public class AttendantControllerTest {

    @MockBean
    private AttendantService service;

    @InjectMocks
    private AttendantController controller;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetAll() throws Exception {
        List<Attendant> attendants = Arrays.asList(new Attendant(), new Attendant());
        when(service.getAll(any(SectorEnum.class))).thenReturn(attendants);

        mockMvc.perform(get("/api/attendant"))
            .andExpect(status().isOk());
    }

    @Test
    public void testGetById_Found() throws Exception {
        Attendant attendant = new Attendant();
        when(service.getById("1")).thenReturn(attendant);

        mockMvc.perform(get("/api/attendant/1"))
            .andExpect(status().isOk());
    }

    @Test
    public void testGetById_NotFound() throws Exception {
        when(service.getById("1")).thenReturn(null);

        mockMvc.perform(get("/api/attendant/1"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void testCreate() throws Exception {
        Attendant attendant = new Attendant();
//        when(service.save(any(Attendant.class))).thenReturn(null);

        mockMvc.perform(post("/api/attendant")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(attendant)))
            .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateRequest_Found() throws Exception {
        Attendant updatedAttendant = new Attendant();
        when(service.update(any(String.class), any(Attendant.class))).thenReturn(true);

        mockMvc.perform(put("/api/attendant/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(updatedAttendant)))
            .andExpect(status().isOk());
    }

    @Test
    public void testUpdateRequest_NotFound() throws Exception {
        when(service.update(any(String.class), any(Attendant.class))).thenReturn(false);

        mockMvc.perform(put("/api/attendant/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(new Attendant())))
            .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteRequest_Found() throws Exception {
        when(service.deleteById("1")).thenReturn(true);

        mockMvc.perform(delete("/api/attendant/1"))
            .andExpect(status().isOk());
    }

    @Test
    public void testDeleteRequest_NotFound() throws Exception {
        when(service.deleteById("1")).thenReturn(false);

        mockMvc.perform(delete("/api/attendant/1"))
            .andExpect(status().isNotFound());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
