package miu.edu.controller;

import lombok.RequiredArgsConstructor;
import miu.edu.dto.UserDTO;
import miu.edu.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/profile")
@RequiredArgsConstructor
@CrossOrigin
public class ProfileController {
    private final UserServiceImpl service;
    @PutMapping("/payment-method")
    public void update(Principal principal, @RequestBody UserDTO user) {
        service.updatePaymentMethod(Long.valueOf(principal.getName()), user.getPreferredPayment());
    }
}
