package miu.edu.client;

import miu.edu.dto.AvailabilityDTO;
import miu.edu.dto.BetweenDateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "product-service", url = "${product-service.ribbon.listOfServers}")
public interface ProductClient {
    @PutMapping("{id}/make-unavailable-during")
    void makeUnavailableBetween(@PathVariable Long id, @RequestBody BetweenDateDTO between);
    @GetMapping("{id}/availability")
    AvailabilityDTO availability(@PathVariable Long id, @RequestBody BetweenDateDTO between);
}
