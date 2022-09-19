package miu.edu.payment.client;

import miu.edu.payment.dto.PaymentMethodDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "account-service", url = "${account-service.ribbon.listOfServers}")
public interface AccountClient {
    @GetMapping("/api/payment-method")
    PaymentMethodDTO getPaymentMethod();
}
