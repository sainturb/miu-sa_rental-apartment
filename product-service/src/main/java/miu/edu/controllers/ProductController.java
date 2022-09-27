package miu.edu.controllers;

import miu.edu.models.BetweenDateDTO;
import miu.edu.models.Product;
import miu.edu.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/products")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping
    public List<Product> getAll() {
        return service.getAll();
    }

    @GetMapping("count")
    public long getCount() {
        return service.getCount();
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

    @PutMapping("{id}/make-unavailable-during")
    public void makeUnavailableBetween(@PathVariable Long id, @RequestBody BetweenDateDTO between) {
        service.makeUnavailableBetween(id, between);
    }

    @PostMapping("{id}/availability")
    public Map<String, Object> availability(@PathVariable Long id, @RequestBody BetweenDateDTO between) {
        return service.getAvailability(id, between);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
