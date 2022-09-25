package miu.edu.service;

import miu.edu.aop.SendNotification;
import miu.edu.dto.OrderStatusDTO;
import miu.edu.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class KafkaConsumer {

    @Autowired
    OrderService orderService;
    @Autowired
    ActivityService activityService;

    @KafkaListener(topics = "order.events", groupId = "group-1",
            containerFactory = "orderKafkaListenerContainerFactory")
    @SendNotification(type = "order")
    public void orderListener(OrderStatusDTO orderStatus) {
        System.out.println("Received message: " + orderStatus);
        Optional<Order> optional = orderService.getByOrderNumber(orderStatus.getOrderNumber());
        optional.ifPresent(order -> {
            var prevStatus = order.getStatus();
            order.setStatus(orderStatus.getStatus());
            order.setNote(orderStatus.getMessage());
            order = orderService.save(order);
            activityService.save(order, prevStatus);
        });
    }
}
