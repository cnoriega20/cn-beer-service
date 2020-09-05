package api.tn.msvc.cnbeerservice.controller;

import api.tn.msvc.cnbeerservice.model.Beer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testGetBeerById() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/api/v1/beerService/" +
                UUID.randomUUID().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testSaveBeer() throws Exception {
        Beer beer = Beer.builder().build();
        String beerToJson = objectMapper.writeValueAsString(beer);
        mockMvc.perform(post("/api/v1/tn/beerService/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(beerToJson))
        .andExpect(status().isCreated());
    }

    @Test
    void testUpdateBeerById() throws Exception {
        Beer beer = Beer.builder().build();
        String beerToJson = objectMapper.writeValueAsString(beer);

        mockMvc.perform(put("/api/v1/tn/beerService/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerToJson))
                .andExpect(status().isNoContent());
    }
    // TODO: Fix Validation errors
}