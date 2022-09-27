package miu.edu.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miu.edu.client.AccountClient;
import miu.edu.client.ProductClient;
import miu.edu.dto.BetweenDateDTO;
import miu.edu.dto.NotificationDTO;
import miu.edu.dto.OrderStatusDTO;
import miu.edu.model.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {
    private final OrderService orderService;
    private final ActivityService activityService;
    private final AccountClient accountClient;
    private final ProductClient productClient;
    private final KafkaTemplate<String, NotificationDTO> kafkaTemplate;

    @KafkaListener(
            topics = "order.events",
            groupId = "group-1",
            containerFactory = "orderStatusKafkaListenerContainerFactory")
    public void orderListener(OrderStatusDTO orderStatus) {
        Optional<Order> optional = orderService.getByOrderNumber(orderStatus.getOrderNumber());
        optional.ifPresent(order -> {
            var prevStatus = order.getStatus();
            order.setStatus(orderStatus.getStatus());
            order.setNote(orderStatus.getMessage());
            order = orderService.save(order);
            if (order.getStatus().equals("paid")) {
                BetweenDateDTO between = new BetweenDateDTO();
                between.setStartDate(order.getStartDate());
                between.setEndDate(order.getEndDate());
                try {
                    productClient.makeUnavailableBetween(order.getProductId(), between);
                } catch (FeignException e) {
                    e.printStackTrace();
                }
            }
            sendNotification(prevStatus, order);
        });
    }

    public void sendNotification(String prevStatus, Order order) {
        try {
            Map<String, String> body = accountClient.retrieveInfo(order.getUserId());
            NotificationDTO notification = new NotificationDTO();
            notification.setSubject(String.format("Order status updated %s", order.getOrderNumber()));
            notification.setText(String.format("Hello %s, \nYour order %s has updated. It changed to %s. \nExtra note: %s", body.get("fullname"), order.getOrderNumber(), order.getStatus(), order.getNote()));
            notification.setTo(body.get("email"));
            kafkaTemplate.send("notification.events", notification);
            activityService.save(order, prevStatus);
        } catch (FeignException e) {
            e.printStackTrace();
        }
    }
}
