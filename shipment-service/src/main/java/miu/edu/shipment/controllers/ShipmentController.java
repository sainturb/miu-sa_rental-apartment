package miu.edu.shipment.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miu.edu.shipment.dto.AddressDTO;
import miu.edu.shipment.services.RestService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@Slf4j
public class ShipmentController {

    private final RestService restService;

    @PostMapping("ship-to-address/{orderNumber}")
    public void shipToAddress(@PathVariable String orderNumber, @RequestBody AddressDTO address) {
        restService.orderStatus(orderNumber, "shipped", String.format("Shipped to %s", address.getAddress()));
        log.info("Shipped to {}", address.getAddress());
    }
    @PostMapping("ship/{orderNumber}")
    public void ship(@PathVariable String orderNumber) {
        Optional<AddressDTO> optional = restService.getShippingAddress();
        optional.ifPresent(address -> {
            restService.orderStatus(orderNumber, "shipped", String.format("Shipped to %s", address.getAddress()));
            log.info("Shipped to {}", address.getAddress());
        });

    }
}
