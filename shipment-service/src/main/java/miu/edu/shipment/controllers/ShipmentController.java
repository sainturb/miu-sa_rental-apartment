package miu.edu.shipment.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miu.edu.shipment.dto.AddressDTO;
import miu.edu.shipment.services.RestService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@Slf4j
public class ShipmentController {

    private final RestService restService;
    @PostMapping("ship/{orderNumber}")
    public void ship(@PathVariable String orderNumber, @RequestBody AddressDTO address) {
        if (Objects.isNull(address)) {
            Optional<AddressDTO> optional = restService.getShippingAddress();
            if (optional.isPresent()) {
                address = optional.get();
            } else {
                restService.orderStatus(orderNumber, "failed", "Shipment is required");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing field");
            }
        }
        restService.orderStatus(orderNumber, "shipped", String.format("Shipped to %s", address.getAddress()));
        log.info("Shipped to {}", address.getAddress());
    }
}
