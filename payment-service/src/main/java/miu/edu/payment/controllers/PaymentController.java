package miu.edu.payment.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("api/checkout")
@Slf4j
@RequiredArgsConstructor
public class PaymentController {

    private final ObjectMapper mapper;

    @PostMapping
    public Map<String, String> checkout(@RequestBody Map<String, Object> body, Principal principal) {
        try {
            Map<String, Object> map = mapper.readValue(principal.getName(), new TypeReference<HashMap<String,Object>>(){});
            if (Objects.nonNull(map.get("paymentInfo"))) {

            } else {
                return Map.of("error", "payment method is not set");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Map.of("response", "checkout completed");
    }
}
