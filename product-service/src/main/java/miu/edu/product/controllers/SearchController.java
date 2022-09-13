package miu.edu.product.controllers;

import lombok.RequiredArgsConstructor;
import miu.edu.product.models.Product;
import miu.edu.product.services.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/search")
@RequiredArgsConstructor
public class SearchController {
    private final ProductService service;

    @GetMapping
    public List<Product> getAll(@RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "description", required = false) String description,
                                @RequestParam(value = "category", required = false) String category,
                                @RequestParam(value = "price.lessThan", required = false) Double priceLessThan,
                                @RequestParam(value = "price.greaterThan", required = false) Double priceGreaterThan
                       ) {
        return service.query(name, description, category, priceLessThan, priceGreaterThan);
    }
}
