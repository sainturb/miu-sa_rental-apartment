package miu.edu.product.services;

import lombok.extern.slf4j.Slf4j;
import miu.edu.product.models.BetweenDateDTO;
import miu.edu.product.models.Product;
import miu.edu.product.models.Review;
import miu.edu.product.repositories.ProductRepository;
import miu.edu.product.search.ProductSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional
@Slf4j
public class ProductService {
    @Autowired
    private ProductRepository repository;
    @Autowired
    private ProductSearchRepository searchRepository;

    public List<Product> getAll() {
        return repository.findAll();
    }

    public long getCount() {
        return repository.count();
    }

    public Optional<Product> getById(Long id) {
        return repository.findById(id);
    }

    public Product save(Product product) {
        searchRepository.save(product);
        return repository.save(product);
    }

    public void delete(Long id) {
        searchRepository.deleteById(id);
        repository.deleteById(id);
    }

    public void makeUnavailableBetween(Long id, BetweenDateDTO between) {
        Optional<Product> productOptional = repository.findById(id);
        productOptional.ifPresent(product -> {
            product.setAvailableFrom(between.getEndDate().plusDays(1));
            save(product);
            log.info("{} between these day it will be available: from {} - until {}", product.getAddress(), product.getAvailableFrom(), product.getAvailableUntil());
        });
    }

    public Map<String, Object> getAvailability(Long id, BetweenDateDTO between) {
        Optional<Product> optional = repository.findById(id);
        if (optional.isPresent()) {
            var available = optional.get().getAvailableFrom().isBefore(between.getStartDate()) && optional.get().getAvailableUntil().isAfter(between.getEndDate());
            return Map.of("available", available, "from", optional.get().getAvailableFrom(), "until", optional.get().getAvailableUntil());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
    }

    public void updateRating(Long productId, List<Review> reviews) {
        var count = 1 + reviews.size();
        AtomicReference<Double> total = new AtomicReference<>((double) 5);
        this.getById(productId)
                .ifPresent(product -> {
                    reviews.stream()
                            .map(Review::getRating)
                            .reduce(Double::sum)
                            .ifPresent(sum -> total.getAndUpdate(current -> current + sum));
                    product.setRating(total.get() / count);
                    this.save(product);
                });
    }
}
