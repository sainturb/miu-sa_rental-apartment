package miu.edu.payment;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "custom")
@Data
public class CustomProperties {
    private String accountService;
    private String orderService;
}
