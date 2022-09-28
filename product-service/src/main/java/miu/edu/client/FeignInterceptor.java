package miu.edu.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import miu.edu.security.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Component
@Slf4j
public class FeignInterceptor implements RequestInterceptor {
//    @Autowired
//    private HttpServletRequest httpServletRequest;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void apply(RequestTemplate template) {
        log.info("Intercepted the feign");
        if (Objects.nonNull(RequestContextHolder.getRequestAttributes())) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            String accessToken = attributes.getRequest().getHeader("Authorization");
            template.header("Authorization",accessToken);
        }
        String serviceToken = jwtHelper.generateServiceToken(applicationContext.getId());
        template.header("ServiceToken", serviceToken);
    }
}