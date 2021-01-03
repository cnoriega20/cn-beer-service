package api.tn.msvc.cnbeerservice.services;

import api.tn.msvc.cnbeerservice.domain.BeerEntity;
import api.tn.msvc.cnbeerservice.exceptions.BeerNotFoundException;
import api.tn.msvc.cnbeerservice.mappers.BeerMapper;
import api.tn.msvc.cnbeerservice.model.Beer;
import api.tn.msvc.cnbeerservice.model.BeerPagedList;
import api.tn.msvc.cnbeerservice.model.BeerStyleEnum;
import api.tn.msvc.cnbeerservice.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;

    private final BeerMapper beerMapper;

    @Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand == false")
    @Override
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand) {

        BeerPagedList beerPagedList;
        Page<BeerEntity> beerPage;

        if(!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName,
                    beerStyle, pageRequest);
        } else if(!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)) {
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);

        } else if(StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)){
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        if(showInventoryOnHand){
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map(beerMapper::mapBeerEntityToBeerWithInventoryData)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(beerPage.getPageable().getPageNumber(),
                                    beerPage.getPageable().getPageSize()),

                    beerPage.getTotalElements());
        } else {
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map(beerMapper::mapBeerEntityToBeer)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(beerPage.getPageable().getPageNumber(),
                                    beerPage.getPageable().getPageSize()),

                    beerPage.getTotalElements());
        }

        return beerPagedList;
    }

    @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventoryOnHand == false")
    @Override
    public Beer getById(UUID beerId, Boolean showInventoryOnHand) {
        if(showInventoryOnHand){
            return beerMapper.mapBeerEntityToBeerWithInventoryData(
                    beerRepository.findById(beerId)
                            .orElseThrow(() -> new BeerNotFoundException(beerId))
            );
        } else {
            return beerMapper.mapBeerEntityToBeer(
                    beerRepository.findById(beerId)
                            .orElseThrow(() -> new BeerNotFoundException(beerId))
            );
        }

    }

    @Cacheable(cacheNames = "beerUpcCache")
    @Override
    public Beer findByUpc(String upc) {
        BeerEntity beerEntity = beerRepository.findByUpc(upc);
        return beerMapper.mapBeerEntityToBeer(beerEntity);
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
