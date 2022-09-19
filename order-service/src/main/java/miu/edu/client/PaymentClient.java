package miu.edu.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "payment-service", url = "${payment-service.ribbon.listOfServers}")
public interface PaymentClient {
    @PostMapping("api/checkout")
    void checkout(@RequestBody Map<String, Object> body);
}
