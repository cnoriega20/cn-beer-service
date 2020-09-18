package api.tn.msvc.cnbeerservice.controller;

import api.tn.msvc.cnbeerservice.model.Beer;
import api.tn.msvc.cnbeerservice.model.BeerStyleEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
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
        ResultActions resultActions = mockMvc.perform(get("/api/v1/tn/beerService/" +
                UUID.randomUUID().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testSaveBeer() throws Exception {
        Beer beer = Beer.builder()
                .beerName("Heineken")
                .price(new BigDecimal(17))
                .beerStyle(BeerStyleEnum.PILSNER)
                .upc(23667891L)
                .build();
        String beerToJson = objectMapper.writeValueAsString(beer);
        mockMvc.perform(post("/api/v1/tn/beerService/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(beerToJson))
        .andExpect(status().isCreated());
    }

    @Test
    void testUpdateBeerById() throws Exception {
        Beer beer = Beer.builder()
                .beerName("Cuzquena")
                .price(new BigDecimal(25))
                .beerStyle(BeerStyleEnum.PILSNER)
                .upc(2366791L)
                .build();
        String beerToJson = objectMapper.writeValueAsString(beer);

        mockMvc.perform(put("/api/v1/tn/beerService/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerToJson))
                .andExpect(status().isNoContent());
    }
    // TODO: Fix Validation errors
}