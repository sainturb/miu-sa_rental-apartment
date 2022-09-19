package miu.edu.client;

import miu.edu.dto.AvailabilityDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "product-service", url = "${product-service.ribbon.listOfServers}")
public interface ProductClient {
    @PutMapping("/api/products/{id}/reduce-stocks/{count}")
    void reduceStocks(@PathVariable Long id, @PathVariable Integer count);

    @GetMapping("/api/products/{id}/availability/{count}")
    AvailabilityDTO availability(@PathVariable Long id, @PathVariable Integer count);
}
