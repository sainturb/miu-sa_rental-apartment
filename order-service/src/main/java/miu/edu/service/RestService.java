package miu.edu.service;

import lombok.RequiredArgsConstructor;
import miu.edu.config.OrderProperties;
import miu.edu.dto.AvailabilityDTO;
import miu.edu.dto.PlaceOrderDTO;
import miu.edu.model.Order;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RestService {

    private final OrderProperties properties;

    private final RestTemplate restTemplate;

    public void reduceStock(String bearerToken, Order order) {
        order.getItems().forEach(item -> {
            URI uri = URI.create(properties.getPaymentService() + "/api/" + item.getProductId() + "/reduce-stocks/" + item.getQuantity());
            HttpEntity<Object> request = new HttpEntity<>(headers(bearerToken));
            restTemplate.put(uri, request);
        });
    }

    public List<AvailabilityDTO> checkAvailable(String bearerToken, PlaceOrderDTO order) {
        List<AvailabilityDTO> list = new ArrayList<>();
        order.getItems().forEach(item -> {
            URI uri = URI.create(properties.getPaymentService() + "/api/" + item.getProductId() + "/availability/" + item.getQuantity());
            HttpEntity<AvailabilityDTO> request = new HttpEntity<>(headers(bearerToken));
            ResponseEntity<AvailabilityDTO> requestResponse = restTemplate.exchange(uri, HttpMethod.GET, request, AvailabilityDTO.class);
            if (requestResponse.getStatusCode() == HttpStatus.OK) {
                list.add(requestResponse.getBody());
            }
        });
        return list;
    }

    public void paymentInitialize(String bearerToken, Map<String, Object> paymentInfo, Map<String, Object> address, Order order) {
        URI uri = URI.create(properties.getPaymentService() + "/api/pay");
        Map<String, Object> map = new HashMap<>();
        if (Objects.nonNull(paymentInfo)) {
            map.put("methodInfo", paymentInfo);
        }
        if (Objects.nonNull(address)) {
            map.put("address", address);
        }
        map.put("orderNumber", order.getOrderNumber());
        map.put("totalAmount", order.getTotalAmount());
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers(bearerToken));
        restTemplate.put(uri, request);
    }

    private HttpHeaders headers(String bearerToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", List.of(bearerToken));
        return headers;
    }

}
