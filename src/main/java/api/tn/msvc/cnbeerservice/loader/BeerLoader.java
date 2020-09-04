package api.tn.msvc.cnbeerservice.loader;

import api.tn.msvc.cnbeerservice.domain.BeerEntity;
import api.tn.msvc.cnbeerservice.repositories.BeerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class BeerLoader implements CommandLineRunner {
    private final BeerRepository beerRepository;


    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        loadBeer();
    }

    private void loadBeer() {
        if(beerRepository.count() == 0){

            beerRepository.save(BeerEntity.builder()
                    .beerName("Miller Lite")
                    .beerStyle("PILSNER")
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .upc(33389917765L)
                    .price(new BigDecimal("11.75"))
                    .build());
            beerRepository.save(BeerEntity.builder()
                    .beerName("Shiner Bock")
                    .beerStyle("Bock")
                    .quantityToBrew(200)
                    .minOnHand(24)
                    .upc(33389917764L)
                    .price(new BigDecimal("12.75"))
                    .build());
            log.info("Loaded beers: " + beerRepository.count());
        }
    }
}
