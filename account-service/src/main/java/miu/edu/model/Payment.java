package miu.edu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
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

