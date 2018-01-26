package backend.elastic.controller;

import backend.elastic.builder.SearchQueryBuilder;
import backend.elastic.model.Dataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/rest/manual/search")
public class ManualSearchResource {

    @Autowired
    private SearchQueryBuilder searchQueryBuilder;

    @GetMapping(value = "/{text}")
    public List<Dataset> getAll(@PathVariable final String text) {
        return searchQueryBuilder.getAll(text);
    }
}
