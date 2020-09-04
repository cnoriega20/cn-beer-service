package api.tn.msvc.cnbeerservice.repositories;

import api.tn.msvc.cnbeerservice.domain.BeerEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface BeerRepository extends PagingAndSortingRepository<BeerEntity, UUID> {
}
