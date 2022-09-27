package miu.edu.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miu.edu.dto.PaymentRequestDTO;
import miu.edu.services.RestService;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("api")
@Slf4j
@RequiredArgsConstructor
public class PaymentController {
    private final RestService rest;

    @PostMapping("checkout")
    public void checkout(@RequestBody PaymentRequestDTO body) {
        if (Objects.isNull(body.getMethodInfo())) {
            body.setMethodInfo(rest.getPaymentMethod());
        }
        log.info("Payment method is {}", body);
        Optional.ofNullable(body.getMethodInfo()).ifPresentOrElse(method -> rest.decidePayment(body),
                () -> rest.failedPayment(body.getOrderNumber(), "Payment method required"));
    }

    @GetMapping("test")
    public void test() {
        rest.test();
    }
}
