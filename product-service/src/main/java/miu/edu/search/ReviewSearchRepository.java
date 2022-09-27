package miu.edu.search;

import miu.edu.models.Review;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.stream.Stream;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

public interface ReviewSearchRepository extends ElasticsearchRepository<Review, Long>, ReviewSearchRepositoryInternal {}

interface ReviewSearchRepositoryInternal {
    Stream<Review> search(String query);

    Stream<Review> search(NativeSearchQuery searchQuery);
}

class ReviewSearchRepositoryInternalImpl implements ReviewSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    ReviewSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Stream<Review> search(String query) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        return elasticsearchTemplate.search(nativeSearchQuery, Review.class).map(SearchHit::getContent).stream();
    }
    @Override
    public Stream<Review> search(NativeSearchQuery nativeSearchQuery) {
        return elasticsearchTemplate.search(nativeSearchQuery, Review.class).map(SearchHit::getContent).stream();
    }
}


