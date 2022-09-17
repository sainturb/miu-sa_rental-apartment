package miu.edu.payment.dto;

import lombok.Data;

import java.util.Map;

@Data
public class PaymentRequestDTO {
    private PaymentMethodDTO methodInfo;
    private Map<String, Object> address;
    private String orderNumber;
    private Double totalAmount;
}

