package miu.edu.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "account-service", url = "${account-service.ribbon.listOfServers}")
public interface AccountClient {
    @GetMapping("api/retrieve-info/{userId}")
    Map<String, String> retrieveInfo(@PathVariable Long userId);
}
