package miu.edu.payment.dto;

import lombok.Data;

@Data
public class OrderStatusDTO {
    private String orderNumber;
    private String status;
    private String message;
}
