package api.tn.msvc.cnbeerservice.services;

import api.tn.msvc.cnbeerservice.model.Beer;
import api.tn.msvc.cnbeerservice.model.BeerPagedList;
import api.tn.msvc.cnbeerservice.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BeerService {

    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand);

    Beer getById(UUID beerId, Boolean showInventoryOnHand);

    Beer saveBeer(Beer beer);

    Beer updateBeer(UUID beerId, Beer beer);
}
