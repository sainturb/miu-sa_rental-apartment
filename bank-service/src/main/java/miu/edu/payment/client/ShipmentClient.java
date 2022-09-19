package miu.edu.payment.client;

import miu.edu.payment.dto.AddressDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "shipment-service", url = "${shipment-service.ribbon.listOfServers}")
public interface ShipmentClient {
    @PostMapping("/api/ship-to-address/{orderNumber}")
    void ship(@PathVariable String orderNumber, @RequestBody AddressDTO address);

    @PostMapping("/api/ship/{orderNumber}")
    void ship(@PathVariable String orderNumber);
}
