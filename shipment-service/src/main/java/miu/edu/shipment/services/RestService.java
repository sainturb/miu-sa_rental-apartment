package miu.edu.shipment.services;

import lombok.RequiredArgsConstructor;
import miu.edu.shipment.config.OrderProperties;
import miu.edu.shipment.dto.AddressDTO;
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
    public Optional<AddressDTO> getShippingAddress(String bearerToken) {
        URI uri = URI.create(properties.getAccountService() + "/api/shipping-address");
        HttpEntity<Void> request = new HttpEntity<>(headers(bearerToken));
        ResponseEntity<AddressDTO> response = restTemplate.exchange(uri, HttpMethod.GET, request, AddressDTO.class);
        return Optional.ofNullable(response.getBody());
    }

    public void orderStatus(String bearerToken, String orderNumber, String status, String reason) {
        URI uri = URI.create(properties.getAccountService() + "/api/update-status/" + orderNumber + "/" + status);
        Map<String, Object> body = new HashMap<>();
        if (Objects.nonNull(reason) && !reason.isEmpty()) {
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