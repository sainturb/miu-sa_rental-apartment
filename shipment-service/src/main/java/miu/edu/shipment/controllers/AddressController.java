package miu.edu.shipment.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miu.edu.shipment.models.Address;
import miu.edu.shipment.services.AddressService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("api/address")
@RequiredArgsConstructor
@Slf4j
public class AddressController {
    private final AddressService service;

    @PostMapping("ship")
    public Map<String, String> ship(Principal principal) {
        return service.ship(Long.valueOf(principal.getName()));
    }

    @PostMapping("shipping")
    public Address shippingAddress(@RequestBody Address address, Principal principal) {
        String username = principal.getName();
        address.setUserId(Long.valueOf(username));
        return service.save(address);
    }

    @PutMapping("shipping/{id}")
    public Address updateAddress(@PathVariable Long id, @RequestBody Address address, Principal principal) {
        String username = principal.getName();
        address.setUserId(Long.valueOf(username));
        address.setId(id);
        return service.save(address);
    }
}
