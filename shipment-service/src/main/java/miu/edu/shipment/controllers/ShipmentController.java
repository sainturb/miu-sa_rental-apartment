package miu.edu.shipment.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miu.edu.shipment.dto.AddressDTO;
import miu.edu.shipment.services.RestService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("api/address")
@RequiredArgsConstructor
@Slf4j
public class ShipmentController {

    private final RestService restService;
    @PostMapping("ship/{orderNumber")
    public void ship(@PathVariable String orderNumber, @RequestBody AddressDTO address, @RequestHeader("Authorization") String bearerToken, Principal principal) {
        if (Objects.isNull(address)) {
            Optional<AddressDTO> optional = restService.getShippingAddress(bearerToken);
            if (optional.isPresent()) {
                address = optional.get();
            } else {
                restService.orderStatus(bearerToken, orderNumber, "failed", "Shipment is required");
            }
        }
        restService.orderStatus(bearerToken, orderNumber, "shipped", String.format("Shipped to %s", address.getAddress()));
        log.info("Shipped to {}", address.getAddress());
    }
}
