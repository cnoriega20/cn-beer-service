package api.tn.msvc.cnbeerservice.repositories;

import api.tn.msvc.cnbeerservice.domain.BeerEntity;
import api.tn.msvc.cnbeerservice.model.BeerStyleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<BeerEntity, UUID> {

    Page<BeerEntity> findAllByBeerName(String beerName, Pageable pageable);

    Page<BeerEntity> findAllByBeerStyle(BeerStyleEnum beerStyle, Pageable pageable);

    Page<BeerEntity>  findAllByBeerNameAndBeerStyle(String beerName, BeerStyleEnum beerStyle, Pageable pageable);

    BeerEntity findByUpc(String upc);

}
