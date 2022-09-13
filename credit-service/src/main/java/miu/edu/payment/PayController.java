package miu.edu.payment;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("api/pay")
public class PayController {
    @PostMapping
    public Map<String, String> pay(@RequestBody Map<String, String> body) {
        if (Objects.nonNull(body.get("card")) && Objects.nonNull(body.get("ccv")) && Objects.nonNull(body.get("expires"))) {
            return Map.of("response", "payment succeed");
        } else {
            return Map.of("error", "required fields are missing");
        }
    }
}
