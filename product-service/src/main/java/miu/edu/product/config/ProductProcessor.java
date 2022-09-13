package miu.edu.product.config;

import lombok.extern.slf4j.Slf4j;
import miu.edu.product.models.Product;
import miu.edu.product.models.ProductDTO;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class ProductProcessor implements ItemProcessor<Product, ProductDTO> {

    @Override
    public ProductDTO process(final Product product) {
        final Long id = product.getId();
        final String name = product.getName();
        final String description = product.getDescription();
        final String category = product.getCategory().toString();
        final Integer stock = product.getStock();
        final double price = product.getPrice();
        final ProductDTO transformedProduct = new ProductDTO(id, category, name, description, stock, price);
        log.info("Converting (" + product + ") into (" + transformedProduct + ")");
        return transformedProduct;
    }

}