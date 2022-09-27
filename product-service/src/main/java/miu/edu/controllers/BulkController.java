package miu.edu.controllers;

import miu.edu.models.Product;
import miu.edu.repositories.ProductRepository;
import miu.edu.search.ProductSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/bulk")
public class BulkController {
    @Autowired
    private ProductSearchRepository searchRepository;

    @Autowired
    private ProductRepository productRepository;

    @PutMapping
    public void bulk() {
        for(int i = 0; i < 10; i++) {
            Pageable pageable = PageRequest.of(i, 1000, Sort.unsorted());
            Page<Product> collection = productRepository.findAll(pageable);
            searchRepository.saveAll(collection.getContent());
        }
    }
}
