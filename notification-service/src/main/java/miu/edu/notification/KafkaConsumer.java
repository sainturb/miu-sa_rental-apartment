package miu.edu.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @Autowired
    MailService mailService;

    @KafkaListener(topics = "notification.events", groupId = "group-1",
            containerFactory = "notificationKafkaListenerContainerFactory")
    public void notificationListener(NotificationDTO notification) {
        System.out.println("Received greeting message: " + notification);
        mailService.sendMessage(notification);
    }
}
