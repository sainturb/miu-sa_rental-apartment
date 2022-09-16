package miu.edu.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import miu.edu.config.OrderProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
//@EnableConfigurationProperties(OrderProperties.class)
public class RestService {

    private final OrderProperties properties;

    private final RestTemplate restTemplate;

    public Map productReduceStock(String bearerToken, Long id, Integer count) {
        URI uri = URI.create(properties.getProductService() + "/api/products/" + id +"/reduce-stocks/" + count);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers(bearerToken));
        return restTemplate.postForObject(uri, request, Map.class);
    }

    public Map paymentInitialize(String bearerToken) {
        URI uri = URI.create(properties.getPaymentService() + "/api/products");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers(bearerToken));
        return restTemplate.postForObject(uri, request, Map.class);
    }

    private HttpHeaders headers(String bearerToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", List.of(bearerToken));
        return headers;
    }

}
