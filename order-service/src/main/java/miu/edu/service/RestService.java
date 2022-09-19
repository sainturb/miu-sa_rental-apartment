package miu.edu.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miu.edu.client.PaymentClient;
import miu.edu.client.ProductClient;
import miu.edu.dto.AvailabilityDTO;
import miu.edu.dto.PlaceOrderDTO;
import miu.edu.model.Order;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestService {

    private final ProductClient productClient;
    private final PaymentClient paymentClient;

    public void reduceStock(Order order) {
        order.getItems().forEach(item -> {
            productClient.reduceStocks(item.getProductId(), item.getQuantity());
        });
    }

    public List<AvailabilityDTO> checkAvailable(PlaceOrderDTO order) {
        List<AvailabilityDTO> list = new ArrayList<>();
        order.getItems().forEach(item -> {
            list.add(productClient.availability(item.getProductId(), item.getQuantity()));
        });
        return list;
    }

    public void paymentInitialize(Map<String, Object> paymentInfo, Map<String, Object> address, Order order) {
        Map<String, Object> body = new HashMap<>();
        if (Objects.nonNull(paymentInfo)) {
            body.put("methodInfo", paymentInfo);
        }
        if (Objects.nonNull(address)) {
            body.put("address", address);
        }
        body.put("orderNumber", order.getOrderNumber());
        body.put("totalAmount", order.getTotalAmount());
        log.info(body.toString());
        paymentClient.checkout(body);
    }

}
