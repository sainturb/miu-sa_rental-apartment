package miu.edu.payment.services;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import miu.edu.payment.client.AccountClient;
import miu.edu.payment.client.BankClient;
import miu.edu.payment.client.CreditClient;
import miu.edu.payment.client.PaypalClient;
import miu.edu.payment.dto.OrderStatusDTO;
import miu.edu.payment.dto.PaymentMethodDTO;
import miu.edu.payment.dto.PaymentRequestDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestService {
    private final AccountClient accountClient;
    private final PaypalClient paypalClient;
    private final BankClient bankClient;
    private final CreditClient creditClient;
    private final KafkaTemplate<String, OrderStatusDTO> kafkaTemplate;

    public PaymentMethodDTO getPaymentMethod() {
        return accountClient.getPaymentMethod();
    }

    public void failedPayment(String orderNumber, String message) {
        OrderStatusDTO orderStatus = new OrderStatusDTO();
        orderStatus.setStatus("failed");
        orderStatus.setOrderNumber(orderNumber);
        orderStatus.setMessage(message);
        kafkaTemplate.send("order.events", orderStatus);
//        orderClient.updateStatus(orderNumber, "failed", body);
    }
    public void decidePayment(PaymentRequestDTO paymentRequest) {
        switch (paymentRequest.getMethodInfo().getType()) {
            case "paypal":
                try {
                    paypalClient.checkout(paymentRequest);
                } catch (FeignException e) {
                    failedPayment(paymentRequest.getOrderNumber(), e.getMessage());
                }
                break;
            case "bank":
                try {
                    bankClient.checkout(paymentRequest);
                } catch (FeignException e) {
                    failedPayment(paymentRequest.getOrderNumber(), e.getMessage());
                }
                break;
            default:
                try {
                    creditClient.checkout(paymentRequest);
                } catch (FeignException e) {
                    failedPayment(paymentRequest.getOrderNumber(), e.getMessage());
                }
                break;
        }
    }

    public void test() {
        bankClient.test();
        creditClient.test();
        paypalClient.test();
    }
}
