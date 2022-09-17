package miu.edu.payment.services;

import lombok.RequiredArgsConstructor;
import miu.edu.payment.config.OrderProperties;
import miu.edu.payment.dto.PaymentMethodDTO;
import miu.edu.payment.dto.PaymentRequestDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RestService {

    private final OrderProperties properties;

    private final RestTemplate restTemplate;

    public Optional<PaymentMethodDTO> getPaymentMethod(String bearerToken) {
        URI uri = URI.create(properties.getAccountService() + "/api/payment-method");
        HttpEntity<Void> request = new HttpEntity<>(headers(bearerToken));
        ResponseEntity<PaymentMethodDTO> response = restTemplate.exchange(uri, HttpMethod.GET, request, PaymentMethodDTO.class);
        return Optional.ofNullable(response.getBody());
    }

    public void failedPayment(String bearerToken, String orderNumber, String reason) {
        URI uri = URI.create(properties.getAccountService() + "/api/update-status/" + orderNumber + "/failed");
        Map<String, Object> body = new HashMap<>();
        if (Objects.nonNull(reason)) {
            body.put("reason", reason);
        }
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers(bearerToken));
        restTemplate.put(uri.toString(), request);
    }
    public void decidePayment(String bearerToken, String type, PaymentRequestDTO paymentRequest) {
        URI uri;
        switch (type) {
            case "paypal":
                uri = URI.create(properties.getPaypalService() + "/api/pay");
                break;
            case "bank":
                uri = URI.create(properties.getBankService() + "/api/pay");
                break;
            default: uri = URI.create(properties.getCreditService() + "/api/pay");
                break;
        }
        HttpEntity<PaymentRequestDTO> request = new HttpEntity<>(paymentRequest, headers(bearerToken));
        restTemplate.exchange(uri, HttpMethod.POST, request, PaymentRequestDTO.class);
    }


    private HttpHeaders headers(String bearerToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", List.of(bearerToken));
        return headers;
    }

}
