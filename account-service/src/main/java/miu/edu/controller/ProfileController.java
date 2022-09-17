package miu.edu.controller;

import lombok.RequiredArgsConstructor;
import miu.edu.model.Address;
import miu.edu.model.Payment;
import miu.edu.service.UserServiceImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/profile")
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
    public void updatePaymentMethod(Principal principal, @Validated @RequestBody Payment method) {
        service.updatePaymentMethod(Long.valueOf(principal.getName()), method);
    }

    @PostMapping("/shipping-address")
    public void updateAddress(Principal principal, @Validated @RequestBody Address address) {
        service.updateAddress(Long.valueOf(principal.getName()), address);
    }
}
