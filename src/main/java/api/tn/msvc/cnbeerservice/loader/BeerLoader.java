package api.tn.msvc.cnbeerservice.loader;

import api.tn.msvc.cnbeerservice.domain.BeerEntity;
import api.tn.msvc.cnbeerservice.repositories.BeerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import java.math.BigDecimal;

//@Component
@Slf4j
public class BeerLoader implements CommandLineRunner {

    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";

    private final BeerRepository beerRepository;

    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        if(beerRepository != null)
            loadBeer();
    }

    private void loadBeer() {
        if(beerRepository.count() == 0){

            beerRepository.save(BeerEntity.builder()
                    .beerName("Miller Lite")
                    .beerStyle("PILSNER")
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .price(new BigDecimal("3.75"))
                    .upc(BEER_1_UPC)
                    .build());

            beerRepository.save(BeerEntity.builder()
                    .beerName("Shiner Bock")
                    .beerStyle("Bock")
                    .quantityToBrew(200)
                    .minOnHand(24)
                    .upc(BEER_2_UPC)
                    .price(new BigDecimal("6.25"))
                    .build());

            beerRepository.save(BeerEntity.builder()
                    .beerName("Pilsen Callao")
                    .beerStyle("Bock")
                    .quantityToBrew(200)
                    .minOnHand(24)
                    .upc(BEER_3_UPC)
                    .price(new BigDecimal("10.15"))
                    .build());

            log.info("Loaded beers: " + beerRepository.count());
        }
    }
}
