package api.tn.msvc.cnbeerservice.mappers;

import api.tn.msvc.cnbeerservice.domain.BeerEntity;
import api.tn.msvc.cnbeerservice.model.Beer;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
public interface BeerMapper {

    Beer mapBeerEntityToBeer(BeerEntity beerEntity);

    BeerEntity mapBeerToBeerEntity(Beer beer);
}
