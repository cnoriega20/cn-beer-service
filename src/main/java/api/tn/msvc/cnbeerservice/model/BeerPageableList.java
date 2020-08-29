package api.tn.msvc.cnbeerservice.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class BeerPageableList extends PageImpl<Beer> {
    public BeerPageableList(List<Beer> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public BeerPageableList(List<Beer> content) {
        super(content);
    }
}
