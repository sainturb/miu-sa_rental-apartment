package miu.edu.notification;

import lombok.Data;

@Data
public class NotificationDTO {
    private String to;
    private String subject;
    private String text;
}
