package miu.edu.service;

import lombok.RequiredArgsConstructor;
import miu.edu.dto.NotificationDTO;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender emailSender;

    public void sendMessage(NotificationDTO notification) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@miu.test.edu");
        message.setTo(notification.getTo());
        message.setSubject(notification.getSubject());
        message.setText(notification.getText());
        emailSender.send(message);
    }
}
