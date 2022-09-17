package miu.edu.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("api/pay")
@RequiredArgsConstructor
public class PayController {

    private final RestService rest;
    @PostMapping
    public void pay(@RequestBody PaymentRequestDTO body,
                                   @RequestHeader("Authorization") String bearerToken) {
        if (Objects.nonNull(body.getMethodInfo().getAccountToken())
                && Objects.nonNull(body.getMethodInfo().getAccountNumber())) {
            rest.orderStatus(bearerToken, body.getOrderNumber(), "failed", "Missing information on Paypal transaction");
        } else {
            rest.orderStatus(bearerToken, body.getOrderNumber(), "paid", "Paid using Paypal method");
            rest.shipToAddress(bearerToken, body);
        }
    }
}
