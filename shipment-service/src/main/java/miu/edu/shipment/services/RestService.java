package miu.edu.shipment.services;

import lombok.RequiredArgsConstructor;
import miu.edu.shipment.client.AccountClient;
import miu.edu.shipment.client.OrderClient;
import miu.edu.shipment.dto.AddressDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestService {

    private final AccountClient accountClient;
    private final OrderClient orderClient;
    public Optional<AddressDTO> getShippingAddress() {
        return Optional.ofNullable(accountClient.getShippingAddress());
    }

    public void orderStatus(String orderNumber, String status, String reason) {
        Map<String, Object> body = new HashMap<>();
        if (Objects.nonNull(reason) && !reason.isEmpty()) {
            body.put("reason", reason);
        }
        orderClient.updateStatus(orderNumber, status, body);
    }
  }