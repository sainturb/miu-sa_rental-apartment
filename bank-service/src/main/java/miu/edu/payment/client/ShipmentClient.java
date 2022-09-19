package miu.edu.payment.client;

import miu.edu.payment.dto.AddressDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "shipment-service", url = "${shipment-service.ribbon.listOfServers}")
public interface ShipmentClient {
    @PostMapping("/api/ship")
    void ship(AddressDTO address);
}
