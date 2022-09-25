package miu.edu.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miu.edu.client.PaymentClient;
import miu.edu.client.ProductClient;
import miu.edu.dto.AvailabilityDTO;
import miu.edu.dto.BetweenDateDTO;
import miu.edu.dto.PlaceOrderDTO;
import miu.edu.model.Order;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestService {

    private final ProductClient productClient;
    private final PaymentClient paymentClient;

    public void makeUnavailableBetween(Order order) {
        BetweenDateDTO between = new BetweenDateDTO();
        between.setStartDate(order.getStartDate());
        between.setEndDate(order.getEndDate());
        productClient.makeUnavailableBetween(order.getProductId(), between);
    }

    public AvailabilityDTO checkAvailable(PlaceOrderDTO order) {
        BetweenDateDTO between = new BetweenDateDTO();
        between.setStartDate(order.getItem().getStartDate());
        between.setEndDate(order.getItem().getEndDate());
        return productClient.availability(order.getItem().getProductId(), between);
    }

    public void paymentInitialize(Map<String, Object> paymentInfo, Order order) {
        Map<String, Object> body = new HashMap<>();
        if (Objects.nonNull(paymentInfo)) {
            body.put("methodInfo", paymentInfo);
        }
        body.put("orderNumber", order.getOrderNumber());
        body.put("totalAmount", order.getTotalAmount());
        log.info(body.toString());
        paymentClient.checkout(body);
    }

}
