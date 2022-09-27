package miu.edu.search;

import miu.edu.models.Product;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.stream.Stream;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

public interface ProductSearchRepository extends ElasticsearchRepository<Product, Long>, ProductSearchRepositoryInternal {}

interface ProductSearchRepositoryInternal {
    Stream<Product> search(String query);

    Stream<Product> search(NativeSearchQuery searchQuery);
}

class ProductSearchRepositoryInternalImpl implements ProductSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    ProductSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Stream<Product> search(String query) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        return elasticsearchTemplate.search(nativeSearchQuery, Product.class).map(SearchHit::getContent).stream();
    }
    @Override
    public Stream<Product> search(NativeSearchQuery nativeSearchQuery) {
        return elasticsearchTemplate.search(nativeSearchQuery, Product.class).map(SearchHit::getContent).stream();
    }
}


