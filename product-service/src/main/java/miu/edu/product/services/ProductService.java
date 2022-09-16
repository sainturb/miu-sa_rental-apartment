package miu.edu.product.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miu.edu.product.models.Product;
import miu.edu.product.repositories.ProductRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository repository;

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
    public Map<String, String> reduceStocks(Long id, Integer stock) {
        Optional<Product> productOptional = repository.findById(id);
        if (productOptional.isEmpty()) {
            return Map.of("error", "not found");
        }
        Product product = productOptional.get();
        if (stock < product.getStock()) {
            product.setStock(product.getStock() - stock);
            repository.save(product);
            log.info("{}'s stock reduced by {} and it is now {}", product.getName(), stock, product.getStock());
            return Map.of("response", "stock reduced", "currentCount", product.getStock().toString());
        } else {
            return Map.of("error", "stock exceeded", "currentCount", product.getStock().toString());
        }
    }
}
