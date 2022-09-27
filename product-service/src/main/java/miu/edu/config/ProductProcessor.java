package miu.edu.config;

import lombok.extern.slf4j.Slf4j;
import miu.edu.models.Product;
import miu.edu.models.ProductDTO;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
public class ProductProcessor implements ItemProcessor<ProductDTO, Product> {

    @Override
    public Product process(final ProductDTO product) {
        final Product transformedProduct = new Product();
        transformedProduct.setId(product.getId());
        transformedProduct.setTotalOccupancy(product.getTotalOccupancy());
        transformedProduct.setTotalBedrooms(product.getTotalBedrooms());
        transformedProduct.setTotalBathrooms(product.getTotalBathrooms());
        transformedProduct.setHomeType(product.getHomeType());
        transformedProduct.setAddress(product.getAddress());
        transformedProduct.setSummary(product.getSummary());
        transformedProduct.setHasAirCon(product.isHasAirCon());
        transformedProduct.setHasHeating(product.isHasHeating());
        transformedProduct.setHasInternet(product.isHasInternet());
        transformedProduct.setHasTv(product.isHasTv());
        transformedProduct.setHasKitchen(product.isHasKitchen());
        transformedProduct.setOwnerId(product.getOwnerId());
        transformedProduct.setLatitude(product.getLatitude());
        transformedProduct.setLongitude(product.getLongitude());
        transformedProduct.setPrice(product.getPrice());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate start = LocalDate.parse(product.getAvailableFrom(), formatter);
        LocalDate end = LocalDate.parse(product.getAvailableUntil(), formatter);
        transformedProduct.setAvailableFrom(start);
        transformedProduct.setAvailableUntil(end);
        log.info("Converting (" + product + ") into (" + transformedProduct + ")");
        return transformedProduct;
    }

}