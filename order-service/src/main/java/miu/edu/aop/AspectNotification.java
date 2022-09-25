package miu.edu.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import miu.edu.client.AccountClient;
import miu.edu.dto.NotificationDTO;
import miu.edu.dto.OrderStatusDTO;
import miu.edu.model.Order;
import miu.edu.service.KafkaProducer;
import miu.edu.service.OrderService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Aspect
@Component
public class AspectNotification {
    @Autowired
    KafkaProducer kafkaProducer;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    AccountClient accountClient;
    @Autowired
    OrderService orderService;

    @Pointcut("@annotation(SendNotification)")
    public void trigger() {}

    @After("trigger()")
    public void sendNotification(JoinPoint point) {
        OrderStatusDTO orderStatus = objectMapper.convertValue(point.getArgs()[0], OrderStatusDTO.class);
        Optional<Order> optional = orderService.getByOrderNumber(orderStatus.getOrderNumber());
        optional.ifPresent(order -> {
            try {
                Map<String, String> body = accountClient.retrieveInfo(order.getUserId());
                NotificationDTO notification = new NotificationDTO();
                notification.setSubject(String.format("Order status updated %s", orderStatus.getOrderNumber()));
                notification.setText(String.format("Your order %s has updated. It changed to %s. \n Extra note: %s", orderStatus.getOrderNumber(), orderStatus.getStatus(), orderStatus.getMessage()));
                notification.setTo(body.get("email"));
                kafkaProducer.sendNotification(notification);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
