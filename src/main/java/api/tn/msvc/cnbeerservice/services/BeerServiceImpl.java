package api.tn.msvc.cnbeerservice.services;

import api.tn.msvc.cnbeerservice.domain.BeerEntity;
import api.tn.msvc.cnbeerservice.exceptions.BeerNotFoundException;
import api.tn.msvc.cnbeerservice.mappers.BeerMapper;
import api.tn.msvc.cnbeerservice.model.Beer;
import api.tn.msvc.cnbeerservice.model.BeerPagedList;
import api.tn.msvc.cnbeerservice.model.BeerStyleEnum;
import api.tn.msvc.cnbeerservice.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
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

        beerPagedList = new BeerPagedList(beerPage
        .getContent()
        .stream()
        .map(beerMapper::mapBeerEntityToBeer)
        .collect(Collectors.toList()),
        PageRequest
                .of(beerPage.getPageable().getPageNumber(),
                        beerPage.getPageable().getPageSize()),

        beerPage.getTotalElements());

        /*beerPagedList = new BeerPagedList(
                beerMapper.convertToBeerList(beerPage.getContent()),
                PageRequest.
                        of(beerPage.getPageable().getPageNumber(),
                                beerPage.getPageable().getPageSize()),
                beerPage.getTotalElements());*/

        return beerPagedList;
    }

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
