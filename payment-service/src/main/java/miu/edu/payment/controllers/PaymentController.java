package miu.edu.payment.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miu.edu.payment.dto.PaymentMethodDTO;
import miu.edu.payment.dto.PaymentRequestDTO;
import miu.edu.payment.services.RestService;
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
        Optional<PaymentMethodDTO> optional = Optional.ofNullable(body.getMethodInfo());
        if (Objects.isNull(body.getMethodInfo())) {
            optional = rest.getPaymentMethod();
        }
        optional.ifPresentOrElse(method -> {
            rest.decidePayment(method.getType(), body);
        }, () -> {
            rest.failedPayment(body.getOrderNumber(),"Payment method required");
        });
    }

    @GetMapping("test")
    public void test() {
        rest.test();
    }
}
