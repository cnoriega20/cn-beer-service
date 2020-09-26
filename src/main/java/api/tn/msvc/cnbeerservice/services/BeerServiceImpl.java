package api.tn.msvc.cnbeerservice.services;

import api.tn.msvc.cnbeerservice.domain.BeerEntity;
import api.tn.msvc.cnbeerservice.exceptions.BeerNotFoundException;
import api.tn.msvc.cnbeerservice.mappers.BeerMapper;
import api.tn.msvc.cnbeerservice.model.Beer;
import api.tn.msvc.cnbeerservice.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;

    private final BeerMapper beerMapper;

    @Override
    public Beer getById(UUID beerId) {
        return beerMapper.mapBeerEntityToBeer(
                beerRepository.findById(beerId)
                        .orElseThrow(() -> new BeerNotFoundException(beerId))
        );
    }

    @Override
    public Beer saveBeer(Beer beer) {

        return beerMapper.mapBeerEntityToBeer(beerRepository.save(beerMapper.mapBeerToBeerEntity(beer)));
    }

    @Override
    public Beer updateBeer(UUID beerId, Beer updatedBeerE) {
        BeerEntity beerEntity = beerRepository.findById(beerId)
                                .map(beerE -> {
                                    beerE.setBeerName(updatedBeerE.getBeerName());
                                    beerE.setBeerStyle(updatedBeerE.getBeerStyle().toString());
                                    beerE.setPrice(updatedBeerE.getPrice());
                                    beerE.setUpc(updatedBeerE.getUpc());
                                    return beerRepository.save(beerE);
                                })
                                .orElseGet(() -> {
                                    updatedBeerE.setId(beerId);
                                    return beerRepository.save(beerMapper.mapBeerToBeerEntity(updatedBeerE));
                                });

        return beerMapper.mapBeerEntityToBeer(beerEntity);
    }
}
