package miu.edu.shipment.client;

import miu.edu.shipment.dto.AddressDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "account-service", url = "${account-service.ribbon.listOfServers}")
public interface AccountClient {
    @GetMapping("/api/shipping-address")
    AddressDTO getShippingAddress();
}
