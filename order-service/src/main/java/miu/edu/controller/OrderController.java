package miu.edu.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miu.edu.dto.PlaceOrderDTO;
import miu.edu.model.Order;
import miu.edu.service.OrderService;
import miu.edu.service.RestService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    public Map<String, String> placeOrder(@Validated @RequestBody PlaceOrderDTO placeOrder, @RequestHeader("Authorization") String bearerToken) {
        rest.paymentInitialize(bearerToken, placeOrder.getPaymentInfo(), placeOrder.getAddress(), service.placeOrder(placeOrder));
        return Map.of("response", "Request went through");
    }

    @PutMapping("update-status/{orderNumber}/{status}")
    public void updateStatus(@PathVariable UUID orderNumber, @PathVariable String status, @RequestHeader("Authorization") String bearerToken) {
        Optional<Order> optional = service.getByOrderNumber(orderNumber);
        optional.ifPresent(order -> {
            order.setStatus(status);
            order = service.save(order);
            if (status.equals("paid")) {
                rest.reduceStock(bearerToken, order);
            }
        });
    }
}
