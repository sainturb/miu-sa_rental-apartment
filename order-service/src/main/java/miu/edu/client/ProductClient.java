package miu.edu.client;

import miu.edu.dto.AvailabilityDTO;
import miu.edu.dto.BetweenDateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "product-service", url = "${product-service.ribbon.listOfServers}")
public interface ProductClient {
    @PutMapping("/api/products/{id}/make-unavailable-during")
    void makeUnavailableBetween(@PathVariable Long id, @RequestBody BetweenDateDTO between);
    @PostMapping("/api/products/{id}/availability")
    AvailabilityDTO availability(@PathVariable Long id, @RequestBody BetweenDateDTO between);
}
