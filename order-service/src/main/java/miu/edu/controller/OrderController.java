package miu.edu.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miu.edu.dto.PlaceOrderDTO;
import miu.edu.model.Order;
import miu.edu.service.OrderService;
import miu.edu.service.RestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/orders")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class OrderController {
    private final OrderService service;

    private final RestService rest;

    @GetMapping
    public List<Order> getAll() {
        return service.getAll();
    }

    @PostMapping("place-order")
    public Map<String, String> placeOrder(@RequestBody PlaceOrderDTO orderDTO, @RequestHeader("Authorization") String bearerToken) {
        // talk to payment
//        Map<String, String> paymentProduct = (Map<String, String>) rest.paymentInitialize(bearerToken);
//        if (Objects.nonNull(paymentProduct.get("response"))) {
//
//        }

        orderDTO.getItems().forEach(item -> {
            // talk to product
            Map responseProduct = rest.productReduceStock(bearerToken, Long.valueOf(item.get("productId").toString()), Integer.valueOf(item.get("quantity").toString()));
            log.info(responseProduct.toString());
        });
        Order orderDetail = new Order();
        orderDetail.setOrderDate(Instant.now());
        orderDetail.setOrderNumber(UUID.randomUUID().toString());
        orderDetail.setStatus("completed");
        service.save(orderDetail);
        return Map.of("response", "successfully ordered");
//        return Map.of("error", "failed somewhere");
    }
}
