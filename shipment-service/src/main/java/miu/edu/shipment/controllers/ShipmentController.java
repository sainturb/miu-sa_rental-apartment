package miu.edu.shipment.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miu.edu.dto.AddressDTO;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("api/address")
@RequiredArgsConstructor
@Slf4j
public class ShipmentController {
    @PostMapping("ship")
    public void ship(@RequestBody Optional<AddressDTO> address, Principal principal) {
        if (address.isPresent()) {
            // ship to address
        } else {
            // asks user's address and ship
        }
        // reduce product stock
    }
}
