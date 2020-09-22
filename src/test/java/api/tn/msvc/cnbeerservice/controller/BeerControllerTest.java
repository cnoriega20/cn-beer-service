package api.tn.msvc.cnbeerservice.controller;

import api.tn.msvc.cnbeerservice.domain.BeerEntity;
import api.tn.msvc.cnbeerservice.model.Beer;
import api.tn.msvc.cnbeerservice.model.BeerStyleEnum;
import api.tn.msvc.cnbeerservice.repositories.BeerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BeerRepository beerRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testGetBeerById() throws Exception {
        given(beerRepository.findById(any())).willReturn(Optional.of(BeerEntity.builder().build()));

        mockMvc.perform(get("/api/v1/tn/beerService/{beerId}",
                UUID.randomUUID().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("api/v1/tn/beerService/",
                        pathParameters(
                            parameterWithName("beerId").description("UUID for the desired beer to get")
                ),
                        responseFields(
                                fieldWithPath("id").description("Id of the Beer"),
                                fieldWithPath("version").description("Version number"),
                                fieldWithPath("createdDate").description("Date of creation"),
                                fieldWithPath("lastModifiedDate").description("Date of last update"),
                                fieldWithPath("beerName").description("Beer name"),
                                fieldWithPath("beerStyle").description("Beer style"),
                                fieldWithPath("upc").description("UPC of the Beer"),
                                fieldWithPath("price").description("Price of the Beer"),
                                fieldWithPath("quantityOnHand").description("Quantity on Hand")
                )));
        /*ResultActions resultActions = mockMvc.perform(get("/api/v1/tn/beerService/" +
                UUID.randomUUID().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());*/
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

}