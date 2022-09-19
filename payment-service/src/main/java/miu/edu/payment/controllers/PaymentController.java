package miu.edu.payment.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miu.edu.payment.dto.PaymentMethodDTO;
import miu.edu.payment.dto.PaymentRequestDTO;
import miu.edu.payment.services.RestService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("api/checkout")
@Slf4j
@RequiredArgsConstructor
public class PaymentController {
    private final RestService rest;

    @PostMapping
    public void checkout(@RequestBody PaymentRequestDTO body,
                         @RequestHeader("Authorization") String bearerToken) {
        Optional<PaymentMethodDTO> optional = Optional.ofNullable(body.getMethodInfo());
        if (Objects.isNull(body.getMethodInfo())) {
            optional = rest.getPaymentMethod(bearerToken);
        }
        optional.ifPresentOrElse(method -> {
            rest.decidePayment(bearerToken, method.getType(), body);
        }, () -> {
            rest.failedPayment(bearerToken, body.getOrderNumber(),"Payment method required");
        });
    }
}
