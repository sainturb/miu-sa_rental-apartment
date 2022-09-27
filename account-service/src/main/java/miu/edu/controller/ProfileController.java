package miu.edu.controller;

import lombok.RequiredArgsConstructor;
import miu.edu.model.Payment;
import miu.edu.model.User;
import miu.edu.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@CrossOrigin
public class ProfileController {
    private final UserServiceImpl service;
    @GetMapping("/retrieve-info/{userId}")
    public Map<String, String> retrieveInfo(@PathVariable Long userId) {
        return service.retrieveInfo(userId);
    }
    @GetMapping("/payment-method")
    public Payment getPaymentMethod(Principal principal) {
        return service.getMethod(Long.valueOf(principal.getName()));
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

    @PostMapping("/update-info/{id}")
    public ResponseEntity<User> updateInfo(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        return service.updateInfo(user).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}
