//package miu.edu.service;
//
//import miu.edu.dto.OrderStatusDTO;
//import org.springframework.kafka.annotation.KafkaHandler;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//
//@Component
//@KafkaListener(id = "group-1", topics = "order.events")
//public class KafkaOrderHandler {
//
//    @KafkaHandler
//    public void handleOrder(OrderStatusDTO order) {
//        System.out.println("Greeting received: " + order);
//    }
//
//    @KafkaHandler(isDefault = true)
//    public void unknown(Object object) {
//        System.out.println("Unknown type received: " + object);
//    }
//
//}