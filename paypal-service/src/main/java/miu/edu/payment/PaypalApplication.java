package miu.edu.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class PaypalApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaypalApplication.class, args);
	}

	@Bean
	RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
