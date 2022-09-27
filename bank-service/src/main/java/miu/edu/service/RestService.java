package miu.edu.service;

import lombok.RequiredArgsConstructor;
import miu.edu.dto.OrderStatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestService {
    @Autowired
    private KafkaTemplate<String, OrderStatusDTO> kafkaTemplate;

    public void orderStatus(String orderNumber, String status, String message) {
        OrderStatusDTO orderStatus = new OrderStatusDTO();
        orderStatus.setStatus(status);
        orderStatus.setOrderNumber(orderNumber);
        orderStatus.setMessage(message);
        kafkaTemplate.send("order.events", "message", orderStatus);
    }

}
