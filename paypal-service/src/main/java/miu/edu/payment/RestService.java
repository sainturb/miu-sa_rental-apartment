package miu.edu.payment;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RestService {
    private final RestTemplate restTemplate;
    private final CustomProperties properties;
    public void shipToAddress(String bearerToken, PaymentRequestDTO paymentRequest) {
        URI uri = URI.create(properties.getAccountService() + "/api/ship/" + paymentRequest.getOrderNumber());
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(paymentRequest.getAddress(), headers(bearerToken));
        restTemplate.exchange(uri, HttpMethod.POST, request, Map.class);
    }

    public void orderStatus(String bearerToken, String orderNumber, String status, String reason) {
        URI uri = URI.create(properties.getAccountService() + "/api/update-status/" + orderNumber + "/" + status);
        Map<String, Object> body = new HashMap<>();
        if (Objects.nonNull(reason)) {
            body.put("reason", reason);
        }
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers(bearerToken));
        restTemplate.put(uri.toString(), request);
    }

    private HttpHeaders headers(String bearerToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", List.of(bearerToken));
        return headers;
    }

}
