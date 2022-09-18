package miu.edu.product.controllers;

import lombok.RequiredArgsConstructor;
import miu.edu.product.models.Product;
import miu.edu.product.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @GetMapping
    public List<Product> getAll() {
        return service.getAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return service.getById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public Product save(@RequestBody Product product) {
        return service.save(product);
    }

    @PutMapping("{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        return service.save(product);
    }

    @PutMapping("{id}/reduce-stocks/{count}")
    public void reduceStocks(@PathVariable Long id, @PathVariable Integer count) {
        service.reduceStocks(id, count);
    }

    @GetMapping("{id}/availability/{count}")
    public Map<String, Object> availability(@PathVariable Long id, @PathVariable Integer count) {
        return service.getAvailability(id, count);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
