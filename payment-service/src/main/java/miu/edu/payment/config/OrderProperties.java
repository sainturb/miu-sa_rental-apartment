package miu.edu.payment.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "order")
@Data
public class OrderProperties {
    private String accountService;
    private String creditService;
    private String paypalService;
    private String bankService;
}
