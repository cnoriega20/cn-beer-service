package api.tn.msvc.cnbeerservice.controller;

import api.tn.msvc.cnbeerservice.model.Beer;
import api.tn.msvc.cnbeerservice.model.BeerPagedList;
import api.tn.msvc.cnbeerservice.model.BeerStyleEnum;
import api.tn.msvc.cnbeerservice.services.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/tn/beerService")
public class BeerController {

    private static final Integer  DEFAULT_PAGE_NUMBER = 0;
    private static final Integer  DEFAULT_PAGE_SIZE = 25;

    private final BeerService beerService;

    @GetMapping(produces = {"application/json"}, path="beer")
    public ResponseEntity<BeerPagedList> listBeers(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                   @RequestParam(value = "beerName", required = false) String beerName,
                                                   @RequestParam(value = "beerStyle", required = false) BeerStyleEnum beerStyle,
                                                   @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand) {
        if(showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }

        if(pageNumber == null || pageNumber == 0){
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if(pageSize == null || pageSize < 1){
            pageSize = DEFAULT_PAGE_SIZE;
        }

        BeerPagedList beerList = beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize), showInventoryOnHand);
        return new ResponseEntity<>(beerList, HttpStatus.OK);
    }

    @GetMapping("/{beerId}")
    public ResponseEntity<Beer> getBeerById(@PathVariable("beerId") UUID beerId,
                                            @RequestParam(value = "showInventoryOnHand",required = false)
                                                    Boolean showInventoryOnHand){
        if(showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }
        return new ResponseEntity<Beer>(beerService.getById(beerId, showInventoryOnHand), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Beer> saveBeer(@RequestBody @Validated Beer beer){

       return new ResponseEntity<>(beerService.saveBeer(beer), HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity<Beer> updateBeerById(@PathVariable("beerId") UUID beerId,
                                               @RequestBody @Validated Beer beer){
        return new ResponseEntity<>(beerService.updateBeer(beerId, beer), HttpStatus.NO_CONTENT);
    }
}
