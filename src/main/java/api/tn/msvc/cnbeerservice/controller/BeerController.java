package api.tn.msvc.cnbeerservice.controller;

import api.tn.msvc.cnbeerservice.model.Beer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/tn/beerService")
public class BeerController {

    @GetMapping("/{beerId}")
    public ResponseEntity<Beer> getBeerById(@PathVariable("beerId") UUID beerId){
        //TODO: complete impl
        return new ResponseEntity<Beer>(Beer.builder().build(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Beer> saveBeer(@RequestBody Beer beer){
        // TODO: complete impl
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity<Beer> updateBeerById(@PathVariable("beerId") UUID beerId,
                                               @RequestBody Beer beer){
        // TODO: complete impl
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
