package miu.edu.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miu.edu.dto.AddressDTO;
import miu.edu.dto.UserDTO;
import miu.edu.service.AddressService;
import miu.edu.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("api/address")
@RequiredArgsConstructor
@Slf4j
public class AddressController {
    private final AddressService service;
    private final UserService userService;

    @PostMapping("shipping")
    public ResponseEntity<AddressDTO> shippingAddress(@RequestBody AddressDTO address, Principal principal) {
        Optional<UserDTO> user = userService.getById(Long.valueOf(principal.getName()));
        if (user.isPresent()) {
            address.setUser(user.get());
            return ResponseEntity.ok(service.save(address));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("shipping/{id}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long id, @RequestBody AddressDTO address, Principal principal) {
        address.setId(id);
        return shippingAddress(address, principal);
    }
}
