package miu.edu.payment.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class FeignInterceptor implements RequestInterceptor {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public void apply(RequestTemplate template) {
        log.info("Intercepted the feign");
        template.header("Authorization", httpServletRequest.getHeader("Authorization"));
    }
}