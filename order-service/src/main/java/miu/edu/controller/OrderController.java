package miu.edu.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miu.edu.dto.PlaceOrderDTO;
import miu.edu.model.Order;
import miu.edu.service.OrderService;
import miu.edu.service.RestService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

    @GetMapping("my")
    public List<Order> getAll(Principal principal) {
        return service.getByUserId(Long.valueOf(principal.getName()));
    }

    @GetMapping("my/{orderNumber}")
    public Optional<Order> getAll(@PathVariable String orderNumber, Principal principal) {
        return service.getByOrderNumberAndUserId(orderNumber, Long.valueOf(principal.getName()));
    }

    @PostMapping("place-order")
    public Map<String, String> placeOrder(@Validated @RequestBody PlaceOrderDTO placeOrder, @RequestHeader("Authorization") String bearerToken, Principal principal) {
        rest.paymentInitialize(bearerToken, placeOrder.getPaymentInfo(), placeOrder.getAddress(), service.placeOrder(placeOrder, principal));
        return Map.of("response", "Request went through");
    }

    @PutMapping("update-status/{orderNumber}/{status}")
    public void updateStatus(@PathVariable String orderNumber, @PathVariable String status, @RequestBody Map<String, String> body, @RequestHeader("Authorization") String bearerToken) {
        Optional<Order> optional = service.getByOrderNumber(orderNumber);
        optional.ifPresent(order -> {
            if (status.equals("failed")) {
                order.setReason(body.get("reason"));
            }
            order.setStatus(status);
            order = service.save(order);
            if (status.equals("paid")) {
                rest.reduceStock(bearerToken, order);
            }
        });
    }
}
