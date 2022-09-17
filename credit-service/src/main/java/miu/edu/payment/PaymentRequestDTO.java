package miu.edu.payment;

import lombok.Data;

import java.util.Map;

@Data
public class PaymentRequestDTO {
    private PaymentMethodDTO methodInfo;
    private Map<String, Object> address;
    private String orderNumber;
}

