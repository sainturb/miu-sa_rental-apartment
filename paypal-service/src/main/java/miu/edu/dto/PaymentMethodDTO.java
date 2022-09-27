package miu.edu.dto;

import lombok.Data;

@Data
public class PaymentMethodDTO {
    private String type;
    // for card
    private String cardNumber;
    private String cardSecurityCode;
    private String cardExpires;
    // for bank
    private String bankAccount;
    private String routingNumber;
    private String bankName;
    // for paypal
    private String accountNumber;
    private String accountToken;
}
