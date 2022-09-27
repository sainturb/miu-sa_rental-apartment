package miu.edu.dto;

import lombok.Data;

@Data
public class PaymentRequestDTO {
    private PaymentMethodDTO methodInfo;
    private AddressDTO address;
    private String orderNumber;
}

