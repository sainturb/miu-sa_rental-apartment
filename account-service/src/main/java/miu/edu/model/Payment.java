package miu.edu.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NonNull
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

    @OneToOne
    private User user;

}

