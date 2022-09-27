package miu.edu.client;

import miu.edu.dto.PaymentRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "credit-service", url = "${credit-service.ribbon.listOfServers}")
public interface CreditClient {
    @PostMapping("/api/pay")
    void checkout(@RequestBody PaymentRequestDTO body);

    @GetMapping("/api/test")
    void test();
}
