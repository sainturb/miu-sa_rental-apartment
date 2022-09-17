package miu.edu.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "custom")
@Data
public class OrderProperties {
    private String productService;
    private String paymentService;
}
