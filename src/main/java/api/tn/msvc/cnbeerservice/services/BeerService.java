package api.tn.msvc.cnbeerservice.services;

import api.tn.msvc.cnbeerservice.model.Beer;

import java.util.UUID;

public interface BeerService {
    Beer getById(UUID beerId);

    Beer saveBeer(Beer beer);

    Beer updateBeer(UUID beerId, Beer beer);
}
