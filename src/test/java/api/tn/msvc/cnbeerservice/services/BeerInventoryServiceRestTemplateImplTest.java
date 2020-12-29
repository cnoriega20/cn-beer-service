package api.tn.msvc.cnbeerservice.services;

import api.tn.msvc.cnbeerservice.services.inventory.BeerInventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@Disabled
@SpringBootTest
public class BeerInventoryServiceRestTemplateImplTest {

    @Autowired
    BeerInventoryService beerInventoryService;

    @BeforeEach
    void setUp(){}

    @Test
    void getOnHandInventory(){
        Integer qoh = beerInventoryService.getOnHandInventory(UUID.randomUUID());
        System.out.println("QOH: " + qoh);
    }
}
