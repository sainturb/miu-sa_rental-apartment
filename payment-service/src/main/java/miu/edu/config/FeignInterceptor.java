package miu.edu.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import miu.edu.security.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class FeignInterceptor implements RequestInterceptor {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void apply(RequestTemplate template) {
        log.info("Intercepted the feign");
        String accessToken = httpServletRequest.getHeader("Authorization");
        String serviceToken = jwtHelper.generateServiceToken(applicationContext.getId());
        template.header("Authorization", accessToken);
        template.header("ServiceToken", serviceToken);
    }
}