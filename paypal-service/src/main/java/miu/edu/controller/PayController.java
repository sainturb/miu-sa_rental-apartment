package miu.edu.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miu.edu.dto.PaymentRequestDTO;
import miu.edu.service.RestService;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class PayController {
    private final RestService rest;
    @PostMapping("pay")
    public void pay(@RequestBody PaymentRequestDTO body) {
        if (Objects.isNull(body.getMethodInfo().getAccountNumber())
                || Objects.isNull(body.getMethodInfo().getAccountToken())) {
            rest.orderStatus(body.getOrderNumber(), "failed", "Missing information on paypal transaction");
        } else {
            rest.orderStatus(body.getOrderNumber(), "paid", "Paid using Paypal method");
        }
    }

    @GetMapping("test")
    public void test() {
        log.info("reached test");
    }
}
