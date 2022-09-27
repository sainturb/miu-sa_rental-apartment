package miu.edu.controllers;

import lombok.RequiredArgsConstructor;
import miu.edu.models.Product;
import miu.edu.repositories.ProductRepository;
import miu.edu.search.ProductSearchRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class SearchController {
    private final static List<String> fields = List.of("homeType", "hasTv", "hasKitchen", "hasAirCon", "hasHeating", "hasInternet");
    private final static List<String> ranges = List.of("rating", "totalOccupancy", "totalBedrooms", "totalBathrooms", "price");
    private final static List<String> contains = List.of("address", "summary");
    private final ProductRepository repository;
    private final ProductSearchRepository searchRepository;

    @GetMapping("search")
    public List<Product> getAll(@RequestParam(value = "homeType", required = false) String homeType) {
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

    @GetMapping("_search")
    public List<Product> search(@RequestParam Map<String, Object> params, Pageable pageable) {
        BoolQueryBuilder query = QueryBuilders.boolQuery();
        fields.forEach(field -> {
            if (Objects.nonNull(params.get(field))) {
                TermQueryBuilder term = QueryBuilders.termQuery(field, params.get(field));
                query.must(term);
            }
        });
        ranges.forEach(field -> {
            if (Objects.nonNull(params.get(field))) {
                if (params.get(field).toString().contains(",")) {
                    String[] values = params.get(field).toString().split(",");
                    RangeQueryBuilder range = QueryBuilders.rangeQuery(field);
                    range.gte(Long.valueOf(values[0]));
                    range.lte(Long.valueOf(values[1]));
                    query.must(range);
                } else {
                    TermQueryBuilder term = QueryBuilders.termQuery(field, params.get(field));
                    query.must(term);
                }

            }
        });
        if (Objects.nonNull(params.get("availableFrom"))) {
            RangeQueryBuilder from = QueryBuilders.rangeQuery("availableFrom");
            from.gte(params.get("availableFrom"));
            query.must(from);
        }
        if (Objects.nonNull(params.get("availableUntil"))) {
            RangeQueryBuilder until = QueryBuilders.rangeQuery("availableUntil");
            until.lte(params.get("availableUntil"));
            query.must(until);
        }
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(query)
                .withPageable(pageable)
                .build();
        return StreamSupport.stream(searchRepository.search(searchQuery).spliterator(), false).collect(Collectors.toList());
    }

}
