package api.tn.msvc.cnbeerservice.controller;

import api.tn.msvc.cnbeerservice.model.Beer;
import api.tn.msvc.cnbeerservice.services.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/tn/beerService")
public class BeerController {

    private final BeerService beerService;

    @GetMapping("/{beerId}")
    public ResponseEntity<Beer> getBeerById(@PathVariable("beerId") UUID beerId){

        return new ResponseEntity<Beer>(beerService.getById(beerId), HttpStatus.OK);
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
