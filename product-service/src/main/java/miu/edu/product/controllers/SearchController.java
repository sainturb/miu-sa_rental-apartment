package miu.edu.product.controllers;

import lombok.RequiredArgsConstructor;
import miu.edu.product.models.Product;
import miu.edu.product.repositories.ProductRepository;
import miu.edu.product.search.ProductSearchRepository;
import miu.edu.product.services.ProductService;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class SearchController {
    private final ProductRepository repository;
    private final ProductSearchRepository searchRepository;

    @GetMapping("search")
    public List<Product> getAll(@RequestParam(value = "homeType", required = false) String homeType,
                                @RequestParam(value = "description", required = false) String description,
                                @RequestParam(value = "category", required = false) String category,
                                @RequestParam(value = "price.lessThan", required = false) Double priceLessThan,
                                @RequestParam(value = "price.greaterThan", required = false) Double priceGreaterThan
                       ) {
        return repository.findAllByHomeType(homeType);
    }

    @GetMapping("search/count")
    public long getCount() {
        return repository.count();
    }

    @GetMapping("_search/{query}")
    public List<Product> search(@PathVariable String query) {
        return StreamSupport.stream(searchRepository.search(query).spliterator(), false).collect(Collectors.toList());
    }

}
