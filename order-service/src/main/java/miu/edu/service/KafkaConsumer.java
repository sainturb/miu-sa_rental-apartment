package miu.edu.service;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import miu.edu.client.AccountClient;
import miu.edu.dto.NotificationDTO;
import miu.edu.dto.OrderStatusDTO;
import miu.edu.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextListener;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class KafkaConsumer {
    @Autowired
    OrderService orderService;
    @Autowired
    ActivityService activityService;
    @Autowired
    AccountClient accountClient;
    @Autowired
    private KafkaTemplate<String, NotificationDTO> kafkaTemplate;
    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    @KafkaListener(topics = "order.events", groupId = "group-1",
            containerFactory = "orderStatusKafkaListenerContainerFactory")
    public void orderListener(OrderStatusDTO orderStatus) {
        System.out.println("Received message: " + orderStatus);
        Optional<Order> optional = orderService.getByOrderNumber(orderStatus.getOrderNumber());
        optional.ifPresent(order -> {
            var prevStatus = order.getStatus();
            order.setStatus(orderStatus.getStatus());
            order.setNote(orderStatus.getMessage());
            order = orderService.save(order);
            sendNotification(prevStatus, order);
        });
    }

    public void sendNotification(String prevStatus, Order order) {
        activityService.save(order, prevStatus);
        try {
            log.info(accountClient.toString());
            log.info(Boolean.valueOf(Objects.nonNull(accountClient)).toString());
            Map<String, String> body = accountClient.retrieveInfo(order.getUserId());
            log.info(body.toString());
            NotificationDTO notification = new NotificationDTO();
            notification.setSubject(String.format("Order status updated %s", order.getOrderNumber()));
            notification.setText(String.format("Hello %s, \nYour order %s has updated. It changed to %s. \nExtra note: %s", body.get("fullname"), order.getOrderNumber(), order.getStatus(), order.getNote()));
            notification.setTo(body.get("email"));
            kafkaTemplate.send("notification.events", notification);
        } catch (FeignException e) {
            e.printStackTrace();
        }
    }
}
