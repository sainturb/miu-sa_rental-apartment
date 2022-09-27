package miu.edu.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miu.edu.dto.AvailabilityDTO;
import miu.edu.dto.PlaceOrderDTO;
import miu.edu.model.Activity;
import miu.edu.model.Order;
import miu.edu.service.ActivityService;
import miu.edu.service.OrderService;
import miu.edu.service.RestService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService service;
    private final ActivityService activity;
    private final RestService rest;

    @GetMapping("my")
    public List<Order> getAll(Principal principal) {
        return service.getByUserId(Long.valueOf(principal.getName()));
    }

    @GetMapping("my/{orderNumber}")
    public Order getAll(@PathVariable String orderNumber, Principal principal) {
        return service.getByOrderNumberAndUserId(orderNumber, Long.valueOf(principal.getName())).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }

    @GetMapping("activities/{orderNumber}")
    public List<Activity> getAllActivity(@PathVariable String orderNumber, Principal principal) {
        return activity.getByOrderNumber(orderNumber, Long.valueOf(principal.getName()));
    }

    @PostMapping("place-order")
    @PaymentRequest
    public Order placeOrder(@RequestBody PlaceOrderDTO placeOrder, Principal principal) {
        AvailabilityDTO activity = rest.checkAvailable(placeOrder);
        if (!activity.isAvailable()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product stock is not available at the moment");
        }
        placeOrder.getItem().setPrice(activity.getPrice());
        return service.placeOrder(placeOrder, principal);
    }

//    @PutMapping("update-status/{orderNumber}/{status}")
//    public void updateStatus(@PathVariable String orderNumber, @PathVariable String status, @RequestBody Map<String, String> body) {
//        Optional<Order> optional = service.getByOrderNumber(orderNumber);
//        optional.ifPresent(order -> {
//            var prevStatus = order.getStatus();
//            if (status.equals("failed")) {
//                order.setNote(body.get("note"));
//            }
//            order.setStatus(status);
//            order = service.save(order);
//            if (status.equals("paid")) {
//                rest.makeUnavailableBetween(order);
//            }
//            activity.save(order, prevStatus);
//        });
//    }
}
