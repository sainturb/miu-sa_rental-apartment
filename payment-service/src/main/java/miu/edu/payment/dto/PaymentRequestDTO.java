package miu.edu.payment.dto;

import lombok.Data;

@Data
public class PaymentRequestDTO {
    private PaymentMethodDTO methodInfo;
    private String orderNumber;
    private Double totalAmount;
}

