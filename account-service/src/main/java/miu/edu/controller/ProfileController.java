package miu.edu.controller;

import lombok.RequiredArgsConstructor;
import miu.edu.model.Address;
import miu.edu.model.Payment;
import miu.edu.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Objects;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@CrossOrigin
public class ProfileController {
    private final UserServiceImpl service;

    @GetMapping("/payment-method")
    public Payment getPaymentMethod(Principal principal) {
        return service.getMethod(Long.valueOf(principal.getName()));
    }

    @GetMapping("/shipping-address")
    public Address getAddress(Principal principal) {
        return service.getAddress(Long.valueOf(principal.getName()));
    }
    @PostMapping("/payment-method")
    public void updatePaymentMethod(Principal principal, @RequestBody @Valid Payment method) {
        if (method.getType().equals("bank") &&
                (Objects.isNull(method.getBankAccount()) ||
                        Objects.isNull(method.getBankName()) ||
                        Objects.isNull(method.getRoutingNumber()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing fields for Bank");
        }

        if (method.getType().equals("credit") &&
                (Objects.isNull(method.getCardExpires()) ||
                        Objects.isNull(method.getCardNumber()) ||
                        Objects.isNull(method.getCardSecurityCode()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing fields for Credit");
        }

        if (method.getType().equals("paypal") &&
                (Objects.isNull(method.getAccountNumber()) ||
                        Objects.isNull(method.getAccountToken()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing fields for Paypal");
        }
        service.updatePaymentMethod(Long.valueOf(principal.getName()), method);
    }

    @PostMapping("/shipping-address")
    public void updateAddress(Principal principal, @Valid @RequestBody Address address) {
        service.updateAddress(Long.valueOf(principal.getName()), address);
    }
}
