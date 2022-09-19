//package miu.edu.payment.config;
//
//import feign.RequestInterceptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.servlet.http.HttpServletRequest;
//
//@Configuration
//public class FeignConfiguration {
//    @Autowired
//    private HttpServletRequest httpServletRequest;
//
//    @Bean
//    public RequestInterceptor requestInterceptor() {
//        String fullToken = httpServletRequest.getHeader("Authorization");
//        return requestTemplate -> requestTemplate.header("Authorization", fullToken);
//    }
//}
