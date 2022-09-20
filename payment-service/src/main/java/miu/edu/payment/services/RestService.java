package miu.edu.payment.services;

import lombok.RequiredArgsConstructor;
import miu.edu.payment.client.*;
import miu.edu.payment.dto.PaymentMethodDTO;
import miu.edu.payment.dto.PaymentRequestDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RestService {
    private final AccountClient accountClient;
    private final PaypalClient paypalClient;
    private final BankClient bankClient;
    private final CreditClient creditClient;
    private final OrderClient orderClient;

    public PaymentMethodDTO getPaymentMethod() {
        return accountClient.getPaymentMethod();
    }

    public void failedPayment(String orderNumber, String reason) {
        Map<String, Object> body = new HashMap<>();
        if (Objects.nonNull(reason)) {
            body.put("reason", reason);
        }
        orderClient.updateStatus(orderNumber, "failed", body);
    }
    public void decidePayment(PaymentRequestDTO paymentRequest) {
        switch (paymentRequest.getMethodInfo().getType()) {
            case "paypal":
                paypalClient.checkout(paymentRequest);
                break;
            case "bank":
                bankClient.checkout(paymentRequest);
                break;
            default:
                creditClient.checkout(paymentRequest);
                break;
        }
    }

    public void test() {
        bankClient.test();
//        creditClient.test();
//        paypalClient.test();
    }
}
