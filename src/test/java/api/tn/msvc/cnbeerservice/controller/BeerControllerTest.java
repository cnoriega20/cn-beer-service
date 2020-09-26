package api.tn.msvc.cnbeerservice.controller;

import api.tn.msvc.cnbeerservice.model.Beer;
import api.tn.msvc.cnbeerservice.model.BeerStyleEnum;
import api.tn.msvc.cnbeerservice.services.BeerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BeerService beerService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testGetBeerById() throws Exception {
        given(beerService.getById(any())).willReturn(getValidBeer());

        mockMvc.perform(get("/api/v1/tn/beerService/{beerId}",
                UUID.randomUUID().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("api/v1/tn/beerService-get/",
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

        given(beerService.saveBeer(any())).willReturn(getValidBeer());

        Beer beer = getValidBeer();
        String beerToJson = objectMapper.writeValueAsString(beer);

        ConstrainedFields constrainedFields = new ConstrainedFields(Beer.class);

        mockMvc.perform(post("/api/v1/tn/beerService/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(beerToJson))
        .andExpect(status().isCreated())
        .andDo(document("api/v1/tn/beerService-new/",
                requestFields(
                        constrainedFields.withPath("id").ignored(),
                        constrainedFields.withPath("version").ignored(),
                        constrainedFields.withPath("createdDate").ignored(),
                        constrainedFields.withPath("lastModifiedDate").ignored(),
                        constrainedFields.withPath("beerName").description("Beer name"),
                        constrainedFields.withPath("beerStyle").description("Beer style"),
                        constrainedFields.withPath("upc").description("UPC of the Beer"),
                        constrainedFields.withPath("price").description("Price of the Beer"),
                        constrainedFields.withPath("quantityOnHand").ignored()
        )));
    }

    @Test
    void testUpdateBeerById() throws Exception {
        given(beerService.updateBeer(any(), any())).willReturn(getValidBeer());
        Beer beer = getValidBeer();
        String beerToJson = objectMapper.writeValueAsString(beer);

        mockMvc.perform(put("/api/v1/tn/beerService/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerToJson))
                .andExpect(status().isNoContent());
    }

    private static class ConstrainedFields{
        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
    }

    private Beer getValidBeer() {
        return Beer.builder().beerName("Cuzquena")
                .price(new BigDecimal(4.75))
                .beerStyle(BeerStyleEnum.PILSNER)
                .upc(2366791L)
                .build();
    }
}