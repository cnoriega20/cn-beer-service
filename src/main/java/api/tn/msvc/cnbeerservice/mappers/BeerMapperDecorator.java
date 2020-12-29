package api.tn.msvc.cnbeerservice.mappers;

import api.tn.msvc.cnbeerservice.domain.BeerEntity;
import api.tn.msvc.cnbeerservice.model.Beer;
import api.tn.msvc.cnbeerservice.services.inventory.BeerInventoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class BeerMapperDecorator implements BeerMapper {
    private BeerInventoryService beerInventoryService;
    private BeerMapper beerMapper;

    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
        this.beerInventoryService = beerInventoryService;
    }

    @Autowired
    public void setBeerMapper(BeerMapper beerMapper) {
        this.beerMapper = beerMapper;
    }

    @Override
    public Beer mapBeerEntityToBeer(BeerEntity beerEntity) {
        Beer beer = beerMapper.mapBeerEntityToBeer(beerEntity);
        beer.setQuantityOnHand(beerInventoryService.getOnHandInventory(beerEntity.getId()));
        System.out.println("Inside Decorator:: beer ==> " + beer);
        return beer;
    }

    @Override
    public BeerEntity mapBeerToBeerEntity(Beer beer) {
        return beerMapper.mapBeerToBeerEntity(beer);
    }

    @Override
    public List<Beer> convertToBeerList(List<BeerEntity> beerEntityList) {
        return beerMapper.convertToBeerList(beerEntityList);
    }
}
