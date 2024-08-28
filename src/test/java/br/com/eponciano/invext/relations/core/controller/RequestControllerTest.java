package br.com.eponciano.invext.relations.core.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.eponciano.invext.relations.core.model.Request;
import br.com.eponciano.invext.relations.core.model.RequestUpdate;
import br.com.eponciano.invext.relations.core.model.SectorEnum;
import br.com.eponciano.invext.relations.core.model.StatusRequestEnum;
import br.com.eponciano.invext.relations.core.service.RequestService;

@WebMvcTest(RequestController.class)
public class RequestControllerTest {

    @MockBean
    private RequestService service;

    @InjectMocks
    private RequestController controller;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetAll() throws Exception {
        List<Request> requests = Arrays.asList(new Request(), new Request());
        when(service.getAll(any(SectorEnum.class), any(String.class), any(StatusRequestEnum.class))).thenReturn(requests);

        mockMvc.perform(get("/api/request"))
            .andExpect(status().isOk());
    }

    @Test
    public void testGetById_Found() throws Exception {
        Request request = new Request();
        when(service.getById("1")).thenReturn(request);

        mockMvc.perform(get("/api/request/1"))
            .andExpect(status().isOk());
    }

    @Test
    public void testGetById_NotFound() throws Exception {
        when(service.getById("1")).thenReturn(null);

        mockMvc.perform(get("/api/request/1"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void testCreate() throws Exception {
        Request request = new Request();
        mockMvc.perform(post("/api/request")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(request)))
            .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateRequest_Found() throws Exception {
    	UUID uuid = UUID.randomUUID();
        Request updatedRequest = new Request();
        when(service.update(eq(uuid.toString()), any(RequestUpdate.class))).thenReturn(true);

        mockMvc.perform(put("/api/request/"+uuid.toString())
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(updatedRequest)))
            .andExpect(status().isOk());
    }

    @Test
    public void testUpdateRequest_NotFound() throws Exception {
        when(service.update(eq("1"), any(RequestUpdate.class))).thenReturn(false);

        mockMvc.perform(put("/api/request/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(new Request())))
            .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteRequest_Found() throws Exception {
        when(service.deleteById("1")).thenReturn(true);

        mockMvc.perform(delete("/api/request/1"))
            .andExpect(status().isOk());
    }

    @Test
    public void testDeleteRequest_NotFound() throws Exception {
        when(service.deleteById("1")).thenReturn(false);

        mockMvc.perform(delete("/api/request/1"))
            .andExpect(status().isNotFound());
    }

    private static String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
