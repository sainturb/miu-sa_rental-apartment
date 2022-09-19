package miu.edu.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miu.edu.config.OrderProperties;
import miu.edu.dto.AvailabilityDTO;
import miu.edu.dto.PlaceOrderDTO;
import miu.edu.model.Order;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestService {

    private final OrderProperties properties;

    private final RestTemplate restTemplate;

    public void reduceStock(String bearerToken, Order order) {
        order.getItems().forEach(item -> {
            String uri = properties.getProductService() + "/api/products/" + item.getProductId() + "/reduce-stocks/" + item.getQuantity();
            log.info("Requested URL: {}", uri);
            HttpEntity<Object> request = new HttpEntity<>(headers(bearerToken));
            restTemplate.put(uri, request);
        });
    }

    public List<AvailabilityDTO> checkAvailable(String bearerToken, PlaceOrderDTO order) {
        List<AvailabilityDTO> list = new ArrayList<>();
        order.getItems().forEach(item -> {
            String uri = properties.getProductService() + "/api/products/" + item.getProductId() + "/availability/" + item.getQuantity();
            log.info("Requested URL: {}", uri);
            HttpEntity<AvailabilityDTO> request = new HttpEntity<>(headers(bearerToken));
            ResponseEntity<AvailabilityDTO> requestResponse = restTemplate.exchange(uri, HttpMethod.GET, request, AvailabilityDTO.class);
            if (requestResponse.getStatusCode() == HttpStatus.OK) {
                list.add(requestResponse.getBody());
            }
        });
        return list;
    }

    public void paymentInitialize(String bearerToken, Map<String, Object> paymentInfo, Map<String, Object> address, Order order) {
        String uri = properties.getPaymentService() + "/api/checkout";
        log.info("Requested URL: {}", uri);
        Map<String, Object> body = new HashMap<>();
        if (Objects.nonNull(paymentInfo)) {
            body.put("methodInfo", paymentInfo);
        }
        if (Objects.nonNull(address)) {
            body.put("address", address);
        }
        body.put("orderNumber", order.getOrderNumber());
        body.put("totalAmount", order.getTotalAmount());
        log.info(body.toString());
        HttpEntity<?> request = new HttpEntity<>(body, headers(bearerToken));
        restTemplate.exchange(uri, HttpMethod.POST, request, String.class);
    }

    private HttpHeaders headers(String bearerToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", List.of(bearerToken));
        headers.put("Content-Type", List.of("application/json"));
        return headers;
    }

}
