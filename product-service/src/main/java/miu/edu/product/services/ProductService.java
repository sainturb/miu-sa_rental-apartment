package miu.edu.product.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miu.edu.product.config.CustomProperties;
import miu.edu.product.models.Product;
import miu.edu.product.repositories.ProductRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository repository;
    private final CustomProperties properties;

    public List<Product> getAll() {
        return repository.findAll();
    }

    public Optional<Product> getById(Long id) {
        return repository.findById(id);
    }

    public Product save(Product product) {
        return repository.save(product);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Product> query(String name, String description, String category, Double priceLessThan, Double priceGreaterThan) {
        Specification<Product> specification = Specification.where(null);
        if (Objects.nonNull(name) && !name.isEmpty()) {
            specification = specification.and(valueContains("name", name));
        }
        if (Objects.nonNull(description) && !description.isEmpty()) {
            specification = specification.and(valueContains("description", description));
        }
        if (Objects.nonNull(category) && !category.isEmpty()) {
            specification = specification.and(valueContains("category", category));
        }
        if (Objects.nonNull(priceLessThan) && !priceLessThan.isNaN()) {
            specification = specification.and(lessThan(priceLessThan));
        }
        if (Objects.nonNull(priceGreaterThan) && !priceGreaterThan.isNaN()) {
            specification = specification.and(greaterThan(priceGreaterThan));
        }
        return repository.findAll(specification);
    }

    static Specification<Product> valueContains(String property, Object value) {
        return (product, cq, cb) -> cb.like(product.get(property), "%" + value.toString() + "%");
    }

    static Specification<Product> greaterThan(double value) {
        return (product, cq, cb) -> cb.greaterThan(product.get("price"), value);
    }

    static Specification<Product> lessThan(double value) {
        return (product, cq, cb) -> cb.greaterThan(product.get("price"), value);
    }
    public void reduceStocks(Long id, Integer stock) {
        Optional<Product> productOptional = repository.findById(id);
        productOptional.ifPresent(product -> {
            product.setStock(product.getStock() - stock);
            repository.save(product);
            log.info("{}'s stock reduced by {} and it is now {}", product.getName(), stock, product.getStock());
        });
    }

    public Map<String, Object> getAvailability(Long id, Integer count) {
        Optional<Product> optional = repository.findById(id);
        if (optional.isPresent()) {
            if (optional.get().getStock() < properties.getShortageAmount()) {
                log.info("There is shortage in product {}, current stock is {}", optional.get().getName(), optional.get().getStock());
            }
            return Map.of("available", optional.get().getStock() >= count, "current", optional.get().getStock());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
    }
}
