package api.tn.msvc.cnbeerservice.exceptions;

import java.util.UUID;

public class BeerNotFoundException extends RuntimeException {
    public BeerNotFoundException(UUID id){
        super("Could not found Beer with id " + id);
    }
}
