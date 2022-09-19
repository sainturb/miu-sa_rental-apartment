package miu.edu.shipment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "order-service", url = "${order-service.ribbon.listOfServers}")
public interface OrderClient {
    @PutMapping("/api/orders/update-status/{orderNumber}/{status}")
    void updateStatus(@PathVariable String orderNumber, @PathVariable String status, @RequestBody Map<String, Object> body);
}
